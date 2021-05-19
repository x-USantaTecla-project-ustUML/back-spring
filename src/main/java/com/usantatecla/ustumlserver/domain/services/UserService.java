package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.User;
import com.usantatecla.ustumlserver.domain.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserPersistence userPersistence;
    private JwtService jwtService;

    @Autowired
    public UserService(UserPersistence userPersistence, JwtService jwtService) {
        this.userPersistence = userPersistence;
        this.jwtService = jwtService;
    }

    public String login(String email) {
        User user = this.userPersistence.findByEmail(email);
        return jwtService.createToken(user.getEmail(), user.getRole().name());
    }

    public String create(User user) {
        this.userPersistence.create(user);
        return this.login(user.getEmail());
    }

}
