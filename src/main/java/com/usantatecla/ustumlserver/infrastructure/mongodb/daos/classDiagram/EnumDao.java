package com.usantatecla.ustumlserver.infrastructure.mongodb.daos.classDiagram;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.EnumEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnumDao extends MongoRepository<EnumEntity, String> {
}
