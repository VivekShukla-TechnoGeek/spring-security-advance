package com.technogeek.springsecurityadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class UserRoleMapping {

    @EmbeddedId
    private UserRoleMappingKey userRoleMappingKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // Maps userId in UserRoleMappingKey
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId") // Maps roleId in UserRoleMappingKey
    @JoinColumn(name = "RoleId", nullable = false)
    private Role role;

    public UserRoleMappingKey getUserRoleMappingKey() {
        return userRoleMappingKey;
    }

    public void setUserRoleMappingKey(UserRoleMappingKey userRoleMappingKey) {
        this.userRoleMappingKey = userRoleMappingKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
