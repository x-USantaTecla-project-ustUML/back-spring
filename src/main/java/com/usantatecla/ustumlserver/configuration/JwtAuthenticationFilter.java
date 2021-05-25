package com.usantatecla.ustumlserver.configuration;

import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.domain.services.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private TokenManager tokenManager;

    public JwtAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = this.tokenManager.extractToken(request.getHeader(AUTHORIZATION));
        if (!token.isEmpty()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(Role.PREFIX + this.tokenManager.role(token));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(this.tokenManager.user(token), token, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
