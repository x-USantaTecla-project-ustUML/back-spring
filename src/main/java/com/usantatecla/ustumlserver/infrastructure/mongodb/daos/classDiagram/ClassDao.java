package com.usantatecla.ustumlserver.infrastructure.mongodb.daos.classDiagram;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.ClassEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassDao extends MongoRepository<ClassEntity, String> {
}
