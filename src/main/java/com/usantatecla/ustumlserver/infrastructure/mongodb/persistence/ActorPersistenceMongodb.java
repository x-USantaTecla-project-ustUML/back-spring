package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.persistence.ActorPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ActorDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.ActorEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ActorPersistenceMongodb implements ActorPersistence {

    private ActorDao actorDao;

    @Autowired
    public ActorPersistenceMongodb(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    @Override
    public Actor read(String id) {
        Optional<ActorEntity> actorEntity = this.actorDao.findById(id);
        if (actorEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return actorEntity.get().toActor();
    }
}
