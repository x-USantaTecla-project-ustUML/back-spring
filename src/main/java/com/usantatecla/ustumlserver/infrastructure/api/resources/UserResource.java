package com.usantatecla.ustumlserver.infrastructure.api.resources;

import com.usantatecla.ustumlserver.domain.services.SessionService;
import com.usantatecla.ustumlserver.domain.services.AccountService;
import com.usantatecla.ustumlserver.infrastructure.api.Rest;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.TokenDto;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Rest
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String LOGOUT = "/logout";

    private AccountService accountService;
    private SessionService sessionService;

    @Autowired
    public UserResource(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @PostMapping(value = UserResource.TOKEN)
    public TokenDto login(@AuthenticationPrincipal User activeUser) {
        return new TokenDto(accountService.login(activeUser.getUsername()));
    }

    @PostMapping()
    public void register(@Valid @RequestBody UserDto creationUserDto) {
        this.accountService.create(creationUserDto.toUser());
    }

    @PreAuthorize("authenticated")
    @PostMapping(value = UserResource.LOGOUT)
    public void logout(HttpSession httpSession) {
        this.sessionService.delete(httpSession.getId());
        httpSession.invalidate();
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
