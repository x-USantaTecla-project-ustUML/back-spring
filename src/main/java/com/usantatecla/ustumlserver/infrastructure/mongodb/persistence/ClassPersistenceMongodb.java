package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClassPersistenceMongodb implements ClassPersistence {

    private ClassDao classDao;
    private PackageUpdater packageUpdater;

    @Autowired
    public ClassPersistenceMongodb(ClassDao classDao, PackageUpdater packageUpdater) {
        this.classDao = classDao;
        this.packageUpdater = packageUpdater;
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
        classEntity.setRelationEntities(this.packageUpdater.updateRelationsList(clazz.getRelations()));
        this.classDao.save(classEntity);
    }

}
