package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations.CompositionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompositionDao extends MongoRepository<CompositionEntity, String> {
    void deleteByTarget(MemberEntity target);
}
