package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackageDao extends MongoRepository<PackageEntity, String> {

}
