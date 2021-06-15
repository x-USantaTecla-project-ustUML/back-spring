package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AggregationEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AggregationDao extends MongoRepository<AggregationEntity, String> {
    List<AggregationEntity> findByTarget(MemberEntity target);
}
