package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.InheritanceEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InheritanceDao extends MongoRepository<InheritanceEntity, String> {
    void deleteByTarget(MemberEntity target);
}
