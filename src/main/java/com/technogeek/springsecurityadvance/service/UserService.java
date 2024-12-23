package com.technogeek.springsecurityadvance.service;

import com.technogeek.springsecurityadvance.entity.User;
import com.technogeek.springsecurityadvance.request.UserRegistrationRequest;

public interface UserService {
    User registerNewUser(UserRegistrationRequest request);

    void registerUserMetadata(User user, final String uniqueId, final String encodedPassword);
}
