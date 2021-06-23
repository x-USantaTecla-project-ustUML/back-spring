package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations.InheritanceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InheritanceDao extends MongoRepository<InheritanceEntity, String> {
    void deleteByTarget(MemberEntity target);
}
