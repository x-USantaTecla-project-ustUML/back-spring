package com.usantatecla.ustumlserver.infrastructure.mongodb.daos.useCaseDiagram;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.UseCaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UseCaseDao extends MongoRepository<UseCaseEntity, String> {
}
