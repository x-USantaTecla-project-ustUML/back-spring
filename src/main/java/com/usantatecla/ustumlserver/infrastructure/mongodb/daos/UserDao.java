package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
}
