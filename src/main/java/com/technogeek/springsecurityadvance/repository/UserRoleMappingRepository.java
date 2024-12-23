package com.technogeek.springsecurityadvance.repository;

import com.technogeek.springsecurityadvance.entity.UserRoleMapping;
import com.technogeek.springsecurityadvance.entity.UserRoleMappingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, UserRoleMappingKey> {
}
