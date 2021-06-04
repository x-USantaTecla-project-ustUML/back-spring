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
    private MemberUpdater memberUpdater;

    @Autowired
    public ClassPersistenceMongodb(ClassDao classDao, MemberUpdater memberUpdater) {
        this.classDao = classDao;
        this.memberUpdater = memberUpdater;
    }

    @Override
    public Class read(String id) {
        return this.find(id).toClass();
    }

    ClassEntity find(String id) {
        Optional<ClassEntity> classEntity = this.classDao.findById(id);
        if (classEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return classEntity.get();
    }

    @Override
    public void update(Class clazz) {
        ClassEntity classEntity = new ClassEntity(clazz);
        if (clazz.getId() != null) {
            classEntity = this.find(clazz.getId());
        }
        classEntity.setRelationEntities(this.memberUpdater.updateRelationsList(clazz.getRelations()));
        this.classDao.save(classEntity);
    }

}
