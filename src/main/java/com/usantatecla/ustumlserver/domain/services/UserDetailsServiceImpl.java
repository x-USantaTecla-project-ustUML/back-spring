package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
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

    private AccountDao accountDao;

    @Autowired
    public UserDetailsServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        Account account = accountDao.findByEmail(email).toUser();
        return this.userBuilder(account.getEmail(), account.getPassword(), new Role[]{Role.AUTHENTICATED});
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
