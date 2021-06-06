package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClassPersistenceMongodb implements ClassPersistence {

    private ClassDao classDao;
    private MemberEntityUpdater memberEntityUpdater;

    @Autowired
    public ClassPersistenceMongodb(ClassDao classDao, MemberEntityUpdater memberEntityUpdater) {
        this.classDao = classDao;
        this.memberEntityUpdater = memberEntityUpdater;
    }

    @Override
    public Class read(String id) {
        Optional<ClassEntity> classEntity = this.classDao.findById(id);
        if (classEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return classEntity.get().toClass();
    }

    @Override
    public void update(Class clazz) {
        this.memberEntityUpdater.update(clazz);
    }

}
