package com.technogeek.springsecurityadvance.service;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface CustomPasswordEncoderService extends PasswordEncoder {
    String getFormulateHashedPassword(Integer userId, String uniqueId, String rawPassword);
}
