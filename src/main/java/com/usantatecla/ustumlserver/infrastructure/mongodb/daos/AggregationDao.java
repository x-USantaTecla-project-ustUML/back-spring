package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations.AggregationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AggregationDao extends MongoRepository<AggregationEntity, String> {
    void deleteByTarget(MemberEntity target);
}
