package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AggregationEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.CompositionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AggregationDao extends MongoRepository<AggregationEntity, String> {
}