package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionDao extends MongoRepository<SessionEntity, String> {
    SessionEntity findBySessionId(String sessionId);
}
