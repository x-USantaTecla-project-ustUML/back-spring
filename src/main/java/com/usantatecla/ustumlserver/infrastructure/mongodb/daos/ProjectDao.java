package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectDao extends MongoRepository<ProjectEntity, String> {
}
