package com.technogeek.springsecurityadvance.controller;

import com.technogeek.springsecurityadvance.facade.UserRegistrationFacadeV1;
import com.technogeek.springsecurityadvance.request.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    private final UserRegistrationFacadeV1 userRegistrationFacadeV1;

    public UserAccountController(UserRegistrationFacadeV1 userRegistrationFacadeV1) {
        this.userRegistrationFacadeV1 = userRegistrationFacadeV1;
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        userRegistrationFacadeV1.registerNewUser(request);
    }
}
