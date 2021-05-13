package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassDao extends MongoRepository<ClassEntity, String> {
}
