package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UseDao  extends MongoRepository<UseEntity, String> {
}
