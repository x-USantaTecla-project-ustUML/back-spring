package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberDao extends MongoRepository<MemberEntity, String> {
    MemberEntity findByName(String name);
}
