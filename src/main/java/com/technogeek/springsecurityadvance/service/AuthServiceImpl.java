package com.technogeek.springsecurityadvance.service;

import com.technogeek.springsecurityadvance.auth.CustomAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    /**
     * @param
     */
    @Override
    public void authenticateUser(String username, String password) {
        Authentication authenticationBody = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationProvider.authenticate(authenticationBody);
    }
}
