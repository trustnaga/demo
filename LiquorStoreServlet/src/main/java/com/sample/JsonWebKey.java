package com.sample;
import java.util.List;
import java.util.ArrayList;

public class JsonWebKey {
    private String kty;
    private String use;
    private String kid;
    private String n;
    private String e;
    private String x5t;
    private String issuer;
    private List<String> x5c = new ArrayList<String>();
    public String getKty() {
        return kty;
    }
    public void setKty(String kty) {
        this.kty = kty;
    }
    public String getUse() {
        return use;
    }
    public void setUse(String use) {
        this.use = use;
    }
    public String getKid() {
        return kid;
    }
    public void setKid(String kid) {
        this.kid = kid;
    }
    public String getN() {
        return n;
    }
    public void setN(String n) {
        this.n = n;
    }
    public String getE() {
        return e;
    }
    public void setE(String e) {
        this.e = e;
    }
    public String getX5t() {
        return x5t;
    }
    public void setX5t(String x5t) {
        this.x5t = x5t;
    }
    public String getIssuer() {
        return issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    public List<String> getX5c() {
        return x5c;
    }
    public void setX5c(List<String> x5c) {
        this.x5c = x5c;
    }


}
