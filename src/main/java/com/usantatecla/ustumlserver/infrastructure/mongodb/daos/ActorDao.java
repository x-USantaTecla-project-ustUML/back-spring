package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.ActorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorDao extends MongoRepository<ActorEntity, String> {
}
