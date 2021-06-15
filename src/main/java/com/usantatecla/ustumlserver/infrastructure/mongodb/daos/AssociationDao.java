package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AssociationEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssociationDao extends MongoRepository<AssociationEntity, String> {
    List<AssociationEntity> findByTarget(MemberEntity target);
}
