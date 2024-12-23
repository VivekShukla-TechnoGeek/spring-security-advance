package com.technogeek.springsecurityadvance.service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class CustomPasswordEncoderServiceImpl implements CustomPasswordEncoderService{

    private static final Logger logger = LoggerFactory.getLogger(CustomPasswordEncoderServiceImpl.class);

    private static final int INITIAL_OFFSET = 1, OFFSET = 2 ,
        HASH_ITERATION_COUNT = 1000;
    private static final String ALGO_SHA256 = "SHA-256";


    PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        //passwordEncoder = new BCryptPasswordEncoder(12, new SecureRandom());
        passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 3);
    }

    /**
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return passwordEncoder.encode(rawPassword);
        } catch (RuntimeException e) {
            logger.info("Exception occurred while encoding the password: ", e);
        }
        return "";
    }

    /**
     * @param rawPassword
     * @param encodedPassword
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        } catch (RuntimeException e) {
            logger.info("Exception occurred while performing password match: ", e);
        }
        return false;
    }

    @Override
    public String getFormulateHashedPassword(Integer userId, String uniqueId, String rawPassword) {
        if (StringUtils.isNotEmpty(rawPassword)) {
            byte[] saltBytes = buildCryptographicSalt(userId, uniqueId.toLowerCase(), rawPassword);
            byte[] hashedPassword;
            try {
                hashedPassword = hashPasswordAndSalt(rawPassword, saltBytes, HASH_ITERATION_COUNT);
            } catch (Exception e) {
                logger.error("Exception while Hashing Password & Salt for UserName:" + uniqueId, e);
                hashedPassword = new byte[0];
            }
            byte[] encodedBytes = Base64.getEncoder().encode(hashedPassword);
            return new String(encodedBytes);

        } else {
            return rawPassword;
        }
    }

    public byte[] buildCryptographicSalt(Integer userId, String uniqueId, String rawPassword) {
        StringBuilder credentialBuilder = new StringBuilder()
                .append(uniqueId)
                .append(rawPassword)
                .append(userId)
                .append(rawPassword)
                .append(uniqueId);
        return formulateSaltFromCredential(credentialBuilder.toString());
    }

    private byte[] formulateSaltFromCredential(String credential) {
        StringBuilder formulateSalt = new StringBuilder("");
        for (int i = INITIAL_OFFSET;
             i < credential.length(); i = i + OFFSET) {
            formulateSalt.append(credential.charAt(i));
        }

        byte[] saltBytes = null;
        try {
            saltBytes = formulateSalt.toString().getBytes(StandardCharsets.UTF_8);
        } catch (RuntimeException e) {
            logger.error("Exception occurred while formulating salt: ", e);
            saltBytes = new byte[0];
        }
        return saltBytes;
    }

    private byte[] hashPasswordAndSalt(String password, byte[] salt, int hashIteration) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(ALGO_SHA256);
        digest.reset();
        digest.update(salt);
        byte[] hashedData = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < hashIteration; i++) {
            digest.reset();
            hashedData = digest.digest(hashedData);
        }
        return hashedData;
    }
}
