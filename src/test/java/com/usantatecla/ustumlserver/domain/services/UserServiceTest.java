package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.domain.model.User;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Test
    void testCreateUserEmailAlreadyExist() {
        User user = User.builder().email("a").password("a").role(Role.AUTHENTICATED).build();
        assertThrows(CommandParserException.class, () -> this.userService.create(user));
    }

}
