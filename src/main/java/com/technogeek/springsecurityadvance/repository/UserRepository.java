package com.technogeek.springsecurityadvance.repository;

import com.technogeek.springsecurityadvance.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
