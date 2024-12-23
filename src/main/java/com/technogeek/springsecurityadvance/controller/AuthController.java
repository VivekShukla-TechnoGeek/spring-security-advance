package com.technogeek.springsecurityadvance.controller;

import com.technogeek.springsecurityadvance.request.UserRegistrationRequest;
import com.technogeek.springsecurityadvance.service.AuthService;
import com.technogeek.springsecurityadvance.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class AuthController {



    @Autowired
    private AuthService authService;



    @GetMapping("/auth/login")
    public void login(HttpServletRequest httpRequest) {
        //final String email =
        String authHeader = httpRequest.getHeader("Authorization");

        String base64Credentials = authHeader.substring(6); // Remove "Basic " prefix
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes, StandardCharsets.UTF_8);

        // Split username and password
        String[] values = credentials.split(":", 2);
        final String username = values[0];
        final String password = values[1];

        // Authenticate the user using AuthService
        authService.authenticateUser(username, password);

    }

}
