package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;
import com.usantatecla.ustumlserver.domain.persistence.ActorPersistence;
import com.usantatecla.ustumlserver.domain.persistence.UseCasePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ActorDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UseCaseDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.ActorEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.UseCaseEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UseCasePersistenceMongodb extends MemberPersistenceMongodb implements UseCasePersistence {

    private UseCaseDao useCaseDao;
    private MemberEntityUpdater memberEntityUpdater;

    @Autowired
    public UseCasePersistenceMongodb(UseCaseDao useCaseDao, MemberEntityUpdater memberEntityUpdater) {
        this.useCaseDao = useCaseDao;
        this.memberEntityUpdater = memberEntityUpdater;
    }

    @Override
    public UseCase read(String id) {
        Optional<UseCaseEntity> useCaseEntity = this.useCaseDao.findById(id);
        if (useCaseEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return useCaseEntity.get().toUseCase();
    }

    @Override
    public UseCase update(UseCase useCase) {
        return ((UseCaseEntity) this.memberEntityUpdater.update(useCase)).toUseCase();
    }

    @Override
    public Member deleteRelations(Member member, List<Relation> relations) {
        this.deleteRelations(relations);
        return this.update((UseCase) member);
    }
}
