package com.technogeek.springsecurityadvance.service;

import com.technogeek.springsecurityadvance.entity.*;
import com.technogeek.springsecurityadvance.repository.RoleRepository;
import com.technogeek.springsecurityadvance.repository.UserMetaDataRepository;
import com.technogeek.springsecurityadvance.repository.UserRepository;
import com.technogeek.springsecurityadvance.repository.UserRoleMappingRepository;
import com.technogeek.springsecurityadvance.request.UserRegistrationRequest;
import com.technogeek.springsecurityadvance.util.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMetaDataRepository userMetaDataRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    /**
     * @param request
     */
    @Override
    @Transactional
    public User registerNewUser(UserRegistrationRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setGender(request.gender());
        user.setAddress(request.address());
        user.setCity(request.city());
        user.setState(request.state());
        user.setCountry(request.country());
        user.setPin(request.pin());
        return userRepository.save(user);
    }

    /**
     * @param user
     * @param uniqueId
     * @param encodedPassword
     */
    @Override
    @Transactional
    public void registerUserMetadata(final User user, final String uniqueId, final String encodedPassword) {
        UserMetaData userMetaData = new UserMetaData();
        userMetaData.setUser(user);
        userMetaData.setUniqueId(uniqueId);
        userMetaData.setPassword(encodedPassword);
        userMetaDataRepository.save(userMetaData);

        // register roles with default access.
        registerRoleForNewUser(user);
    }

    @Transactional
    private void registerRoleForNewUser(final User user) {
        Role role = roleRepository.findByName(RoleType.USER.toString());
        UserRoleMappingKey userRoleMappingKey = new UserRoleMappingKey(user.getUserId(), role.getRoleId());
        UserRoleMapping userRoleMapping = new UserRoleMapping();
        userRoleMapping.setUserRoleMappingKey(userRoleMappingKey);
        userRoleMapping.setUser(user);
        userRoleMapping.setRole(role);
        userRoleMappingRepository.save(userRoleMapping);
    }
}
