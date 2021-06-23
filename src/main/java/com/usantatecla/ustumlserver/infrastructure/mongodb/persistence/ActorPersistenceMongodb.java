package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.persistence.ActorPersistence;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ActorDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.ActorEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActorPersistenceMongodb  extends MemberPersistenceMongodb implements ActorPersistence {

    private ActorDao actorDao;
    private MemberEntityUpdater memberEntityUpdater;

    @Autowired
    public ActorPersistenceMongodb(ActorDao actorDao, MemberEntityUpdater memberEntityUpdater) {
        this.actorDao = actorDao;
        this.memberEntityUpdater = memberEntityUpdater;
    }

    @Override
    public Actor read(String id) {
        Optional<ActorEntity> actorEntity = this.actorDao.findById(id);
        if (actorEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return actorEntity.get().toActor();
    }

    @Override
    public Actor update(Actor actor) {
        return ((ActorEntity) this.memberEntityUpdater.update(actor)).toActor();
    }

    @Override
    public Member deleteRelations(Member member, List<Relation> relations) {
        this.deleteRelations(relations);
        return this.update((Actor) member);
    }
}
