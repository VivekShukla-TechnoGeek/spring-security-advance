package com.technogeek.springsecurityadvance.auth;

import java.util.Objects;

public final class UserContext {

    private final Integer userId;
    private final String name;
    private final String email;

    private UserContext(Integer userId, String name, String email) {
        this.userId = Objects.requireNonNull(userId, "UserId must not be null");
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.email = Objects.requireNonNull(email, "Email must not be null");
    }

    /**
     * Factory method to create a new UserContext instance.
     *
     * @return a new UserContext instance
     */
    public static UserContext create(Integer userId, String name, String email) {
        return new UserContext(userId, name, email);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
