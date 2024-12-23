package com.technogeek.springsecurityadvance.entity;

import jakarta.persistence.*;

@Entity
public class UserMetaData {

    @Id
    @Column(name = "UserId")
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "UserId")
    private User user;

    @Column(name = "UniqueId", nullable = false, unique = true)
    private String uniqueId;

    @Column(name = "Password", nullable = false)
    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
