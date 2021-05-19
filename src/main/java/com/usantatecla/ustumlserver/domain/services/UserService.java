package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.persistence.UserPersistence;
import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String create(User user, Role roleClaim) {
        if (!authorizedRoles(roleClaim).contains(user.getRole())) {
            throw new CommandParserException(Error.INVALID_ROLE); // TODO
        }
        this.userPersistence.create(user);
        return this.login(user.getEmail());
    }

    private List<Role> authorizedRoles(Role roleClaim) {
        if (Role.ADMIN.equals(roleClaim)) {
            return List.of(Role.ADMIN, Role.DEVELOPER, Role.AUTHENTICATED);
        } else if (Role.DEVELOPER.equals(roleClaim)) {
            return List.of(Role.DEVELOPER, Role.AUTHENTICATED);
        } else if (Role.AUTHENTICATED.equals(roleClaim)) {
            return List.of(Role.AUTHENTICATED);
        } else {
            return List.of();
        }
    }

}
