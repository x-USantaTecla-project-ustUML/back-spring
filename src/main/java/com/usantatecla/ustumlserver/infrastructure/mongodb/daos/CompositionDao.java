package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.CompositionEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompositionDao extends MongoRepository<CompositionEntity, String> {
    List<CompositionEntity> findByTarget(MemberEntity target);
}
