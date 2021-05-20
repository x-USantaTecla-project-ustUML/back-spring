package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.domain.model.User;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Qualifier("ust.uml.users")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        User user = userDao.findByEmail(email).toUser();
        return this.userBuilder(user.getEmail(), user.getPassword(), new Role[]{Role.AUTHENTICATED});
    }

    private org.springframework.security.core.userdetails.User userBuilder(String email, String password, Role[] roles) {
        List< GrantedAuthority > authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.withPrefix()));
        }
        return new org.springframework.security.core.userdetails.User(email, password, true, true,
                true, true, authorities);
    }
}
