package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersistence {
    User findByEmail(String email);

    void create(User user);

    void save(User user);
}
