package com.sample;

import com.sample.model.LiquorType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@WebServlet(
        name = "UserServlet",
        urlPatterns = "/User"
)
public class UserServlet extends HttpServlet {
   
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        // Add key-value pairs to the HashMap
        HashMap<String, String> user = new HashMap<>();
        user.put("givenname", "John");
        user.put("surname", "Doe");
        user.put("userPrincipalName", "johndoe@example.com");
        user.put("id", "12345");
        JSONObject json = new JSONObject(user);
		resp.setStatus(200);
		resp.setHeader("Content-Type", "application/json");
		resp.getOutputStream().println(json.toString());
	}

   
}
