package com.technogeek.springsecurityadvance.repository;

import com.technogeek.springsecurityadvance.entity.Role;
import com.technogeek.springsecurityadvance.util.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
