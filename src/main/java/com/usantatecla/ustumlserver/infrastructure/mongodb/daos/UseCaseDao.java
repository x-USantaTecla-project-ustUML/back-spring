package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.UseCaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UseCaseDao extends MongoRepository<UseCaseEntity, String> {
}
