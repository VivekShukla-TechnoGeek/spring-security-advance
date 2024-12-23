package com.technogeek.springsecurityadvance.repository;

import com.technogeek.springsecurityadvance.entity.UserMetaData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMetaDataRepository extends CrudRepository<UserMetaData, Integer> {
}
