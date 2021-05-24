package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClassDao extends MongoRepository<ClassEntity, String> {
    Optional<ClassEntity> findById(String id);

    ClassEntity findByName(String name);
}
