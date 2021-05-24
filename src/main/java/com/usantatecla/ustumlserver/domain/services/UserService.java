package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.User;
import com.usantatecla.ustumlserver.domain.persistence.UserPersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
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
/*        Error error = this.userPersistence.exist(email);
        if (!error.isNull()) {
            throw new CommandParserException(error);
        }*/
        User user = this.userPersistence.read(email);
        return jwtService.createToken(user.getEmail(), user.getRole().name());
    }

    public void create(User user) {
        Error error = this.userPersistence.create(user);
        if (!error.isNull()) {
            throw new CommandParserException(error);
        }
    }

}
