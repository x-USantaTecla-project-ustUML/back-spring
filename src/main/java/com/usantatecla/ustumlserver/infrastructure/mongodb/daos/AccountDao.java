package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountDao extends MongoRepository<AccountEntity, String> {
    AccountEntity findByEmail(String email);
}
