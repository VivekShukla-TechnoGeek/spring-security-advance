package com.technogeek.springsecurityadvance.facade;

import com.technogeek.springsecurityadvance.entity.User;
import com.technogeek.springsecurityadvance.request.UserRegistrationRequest;
import com.technogeek.springsecurityadvance.service.CustomPasswordEncoderService;
import com.technogeek.springsecurityadvance.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class UserRegistrationFacadeV1 {

    private final UserService userService;
    private final CustomPasswordEncoderService customPasswordEncoderService;
    final private BCryptPasswordEncoder encoder;

    public UserRegistrationFacadeV1(UserService userService, CustomPasswordEncoderService customPasswordEncoderService) {
        this.userService = userService;
        this.customPasswordEncoderService = customPasswordEncoderService;
        this.encoder = new BCryptPasswordEncoder(10);
    }

    @Transactional
    public void registerNewUser(UserRegistrationRequest request) {
        User user = userService.registerNewUser(request);
        final  String uniqueId = formulateUniqueId(user.getUserId(), user.getEmail());
        final String password = formulateAndEncodePassword(request, user, uniqueId);
        userService.registerUserMetadata(user, uniqueId, password);
    }

    private String formulateAndEncodePassword(UserRegistrationRequest request, User user, String uniqueId) {
        final String password = customPasswordEncoderService.encode(
                customPasswordEncoderService.getFormulateHashedPassword(
                        user.getUserId(), uniqueId, request.pass()));
        return password;
    }

    private String formulateUniqueId(Integer userId, String email) {
        StringBuilder uniqueIdBuilder = new StringBuilder()
                .append(userId)
                .append(email)
                .append(UUID.randomUUID())
                .append(userId);
        final String uniqueId =   encoder.encode(uniqueIdBuilder.toString());
        return uniqueId;
    }

}
