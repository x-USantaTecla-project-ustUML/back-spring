package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.InterfaceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterfaceDao extends MongoRepository<InterfaceEntity, String> {
}
