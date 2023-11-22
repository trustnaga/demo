
package com.sample;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureException;




@WebFilter(filterName = "ADTokenFilter", urlPatterns = "/*")
public class ADTokenFilter implements Filter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
   // private final static String AUTHORITY = "https://login.microsoftonline.com/16b3c013-d300-468d-ac64-7eda0820b6d3";
    private final static String JWKS_URL="https://login.microsoftonline.com/16b3c013-d300-468d-ac64-7eda0820b6d3/discovery/keys";
    // The prefix for the access token
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String clientId = "a5948bd0-10cd-42c7-b066-264ff114ddb9";
    private static final String issuer = "https://login.microsoftonline.com/16b3c013-d300-468d-ac64-7eda0820b6d3/v2.0";

     @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO: Initialize the filter if needed
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String accessToken = httpRequest.getHeader(AUTHORIZATION_HEADER);
        if (accessToken != null && accessToken.startsWith(BEARER_PREFIX)) {
            accessToken = accessToken.substring(BEARER_PREFIX.length());
            System.out.print(accessToken);
            try {
                Jws<Claims> claims = validateAccessToken(accessToken);
                if (claims != null) {
                    // If the access token is valid, store the claims in the session
                    HttpSession session = httpRequest.getSession();
                    session.setAttribute("claims", claims);

                    // Continue with the request
                    chain.doFilter(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Invalid access token\"}");
            }
        } else {
            // If the access token is not present or has the wrong prefix, reject the request with an appropriate error message
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Missing or malformed access token\"}");
        }
    }

    private static Jws<Claims> validateAccessToken(String accessToken) throws Exception {

        SigningKeyResolver signingKeyResolver = new SigningKeyResolver(JWKS_URL);
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKeyResolver(signingKeyResolver)
                    .requireAudience(clientId)
                    .requireIssuer(issuer)
                    //  .require("scp", "openid profile User.Read email")
                    .parseClaimsJws(accessToken);

        } catch(SignatureException ex) {
            throw new JwtValidationException("Jwt validation failed: invalid signature", ex);
        } catch(ExpiredJwtException ex) {
            throw new JwtValidationException("Jwt validation failed: access token us expired", ex);
        } catch(MissingClaimException ex) {
            throw new JwtValidationException("Jwt validation failed: missing required claim", ex);
        } catch(IncorrectClaimException ex) {
            throw new JwtValidationException("Jwt validation failed: required claim has incorrect value", ex);
        }

        return claims;
    }

    @Override
    public void destroy() {
        // TODO: Destroy the filter if needed
    }
    
}
