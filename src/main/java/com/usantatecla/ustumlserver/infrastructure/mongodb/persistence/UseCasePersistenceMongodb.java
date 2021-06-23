package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;
import com.usantatecla.ustumlserver.domain.persistence.UseCasePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UseCaseDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram.UseCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UseCasePersistenceMongodb implements UseCasePersistence {

    private UseCaseDao useCaseDao;

    @Autowired
    public UseCasePersistenceMongodb(UseCaseDao useCaseDao) {
        this.useCaseDao = useCaseDao;
    }

    @Override
    public UseCase read(String id) {
        Optional<UseCaseEntity> useCaseEntity = this.useCaseDao.findById(id);
        if (useCaseEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return useCaseEntity.get().toUseCase();
    }
}
