package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.User;
import com.usantatecla.ustumlserver.domain.services.Error;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersistence {
    User read(String email);

    void create(User user);

    void save(User user);
}
