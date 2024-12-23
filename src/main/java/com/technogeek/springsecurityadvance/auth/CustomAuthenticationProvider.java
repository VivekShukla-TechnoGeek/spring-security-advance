package com.technogeek.springsecurityadvance.auth;

import com.technogeek.springsecurityadvance.entity.User;
import com.technogeek.springsecurityadvance.entity.UserMetaData;
import com.technogeek.springsecurityadvance.repository.UserMetaDataRepository;
import com.technogeek.springsecurityadvance.repository.UserRepository;
import com.technogeek.springsecurityadvance.service.CustomPasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMetaDataRepository userMetaDataRepository;

    @Autowired
    private CustomPasswordEncoderService customPasswordEncoderService;

    /**
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Optional.ofNullable(authentication)
                .orElseThrow(() -> new BadCredentialsException("Credential not provided."));

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserMetaData userMetaData = Optional.ofNullable(userRepository.findByEmail(email))
                .map(user -> userMetaDataRepository.findById(user.getUserId())
                        .orElseThrow(() -> new BadCredentialsException("User metadata not found.")))
                .orElseThrow(() -> new BadCredentialsException("User not found."));

        // Validate password
        boolean isValid = Optional.of(customPasswordEncoderService.getFormulateHashedPassword(
                        userMetaData.getUser().getUserId(),
                        userMetaData.getUniqueId(),
                        password))
                .map(rawPassword -> customPasswordEncoderService.matches(rawPassword, userMetaData.getPassword()))
                .orElse(false);

        if (!isValid) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        // Return the authentication token
        User user = userMetaData.getUser();

        return new UsernamePasswordAuthenticationToken(UserContext.create(user.getUserId(),
                user.getName(), user.getEmail()), null, new ArrayList<>());
    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
