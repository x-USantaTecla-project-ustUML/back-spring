package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AssociationEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssociationDao extends MongoRepository<AssociationEntity, String> {
    void deleteByTarget(MemberEntity target);
}
