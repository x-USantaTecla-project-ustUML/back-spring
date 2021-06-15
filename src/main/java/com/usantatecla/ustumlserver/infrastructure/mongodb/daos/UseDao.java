package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UseDao  extends MongoRepository<UseEntity, String> {
    List<UseEntity> findByTarget(MemberEntity target);
}
