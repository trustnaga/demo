package com.sample;

import com.sample.model.LiquorType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureException;


@WebServlet(
        name = "UserServlet",
        urlPatterns = "/User"
)
public class UserServlet extends HttpServlet {
   
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        HttpSession session = req.getSession();
        Jws<Claims> claims = (Jws<Claims>) session.getAttribute("claims");
        
        String id = (String) claims.getBody().get("oid");
      

        // Add key-value pairs to the HashMap
        HashMap<String, String> user = new HashMap<>();
       
        user.put("Userid", id);
        JSONObject json = new JSONObject(user);
		resp.setStatus(200);
		resp.setHeader("Content-Type", "application/json");
		resp.getOutputStream().println(json.toString());
	}

   
}
