package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.TokenDto;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.UserDto;
import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String TOKEN = "/token";

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public TokenDto login(@AuthenticationPrincipal User activeUser) {
        return new TokenDto(userService.login(activeUser.getUsername()));
    }

    @PostMapping()
    public TokenDto register(@Valid @RequestBody UserDto creationUserDto) {
        creationUserDto.doDefault();
        return new TokenDto(this.userService.create(creationUserDto.toUser(), Role.DEVELOPER));
    }

}
