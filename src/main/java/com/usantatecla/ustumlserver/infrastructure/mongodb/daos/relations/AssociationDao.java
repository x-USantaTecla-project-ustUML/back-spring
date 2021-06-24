package com.usantatecla.ustumlserver.infrastructure.mongodb.daos.relations;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations.AssociationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssociationDao extends MongoRepository<AssociationEntity, String> {
    void deleteByTarget(MemberEntity target);
}
