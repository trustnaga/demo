package com.sample;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

import java.net.MalformedURLException;
import java.io.ByteArrayInputStream;


import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.KeyDeserializer;

import java.security.PublicKey;
import java.security.Key;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import java.security.spec.RSAPublicKeySpec;
import java.security.interfaces.RSAPublicKey;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.net.URL;

import javax.xml.bind.DatatypeConverter;

import java.security.KeyFactory;
import org.apache.commons.io.IOUtils;


public class SigningKeyResolver extends SigningKeyResolverAdapter{
 
    String jwksUrl;
    
    public SigningKeyResolver(String jwksUrl) {
        this.jwksUrl = jwksUrl;
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        AADKeySet keySet;

        try {
            keySet = getKeysFromJwkUri();
            String tokenKeyId = jwsHeader.getKeyId();
            for(JsonWebKey key: keySet.getKeys()){
                if(key.getKid().equalsIgnoreCase(tokenKeyId)){

                    return generatePublicKey(key);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        throw new JwtValidationException("Signature validation failed: Could not find a key with matching kid");
    }

    private Key generatePublicKey(JsonWebKey key) {
        Key publicKey = null;
        try {
            System.err.println("*******key: " + key.toString() + "\n");
            System.err.println("*******key N: " + key.getN() + "\n");
            System.err.println("*******key E: " + key.getE() + "\n");
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.getN()));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.getE()));

           publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
           System.err.println("*******publicKey 1: " + publicKey.toString() + "\n"); 
        //    CertificateFactory factory=  CertificateFactory.getInstance("X.509");
        //     X509Certificate cert = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(key.getX5c().iterator().next())));
        //     publicKey = (RSAPublicKey)cert.getPublicKey();
        //     System.err.println("*******publicKey 2: " + publicKey.toString() + "\n");
        } catch(Exception e){
            throw new JwtValidationException("Key generation failed", e);
        }

        return publicKey;
    }
    private AADKeySet getKeysFromJwkUri() throws IOException {
        AADKeySet keySet = new AADKeySet();
        URL url = new URL(jwksUrl);
        String content = new String(IOUtils.toByteArray(url.openStream()));
        JSONObject contentObject = new JSONObject(content);
        JSONArray keys = contentObject.getJSONArray("keys");
        System.out.print("keys: " + keys.toString() + "\n");
        for (int i = 0; i < keys.length(); i++) {
            JSONObject key = keys.getJSONObject(i);
            System.err.println("*******key: " + key.toString() + "\n");
            JsonWebKey jsonWebKey = new JsonWebKey();
            jsonWebKey.setKty(key.getString("kty"));
            jsonWebKey.setUse(key.getString("use"));
            jsonWebKey.setKid(key.getString("kid"));
            jsonWebKey.setN(key.getString("n"));
            jsonWebKey.setE(key.getString("e"));
            jsonWebKey.setX5t(key.getString("x5t"));
            //jsonWebKey.setIssuer(key.getString("iss"));
            JSONArray x5c = key.getJSONArray("x5c");
            for (int j = 0; j < x5c.length(); j++) {
                jsonWebKey.getX5c().add(x5c.getString(j));
            }
            keySet.getKeys().add(jsonWebKey);
        }
        return keySet;
    }
 

   
}
