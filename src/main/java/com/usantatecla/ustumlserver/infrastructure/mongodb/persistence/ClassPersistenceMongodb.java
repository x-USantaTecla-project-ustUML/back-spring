package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.persistence.ClassPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClassPersistenceMongodb extends MemberPersistenceMongodb implements ClassPersistence {

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
    public Class update(Class clazz) {
        return ((ClassEntity) this.memberEntityUpdater.update(clazz)).toClass();
    }

    @Override
    public Member deleteRelations(Member member, List<Relation> relations) {
        this.deleteRelations(relations);
        return this.update((Class) member);
    }
}
