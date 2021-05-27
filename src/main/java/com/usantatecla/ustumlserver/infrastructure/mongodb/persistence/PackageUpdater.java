package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Repository
class PackageUpdater implements MemberVisitor {

    private PackageDao packageDao;
    private ClassDao classDao;
    private Stack<MemberEntity> memberEntities;

    @Autowired
    PackageUpdater(PackageDao packageDao, ClassDao classDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.memberEntities = new Stack<>();
    }

    PackageEntity update(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        if(pakage.getId() != null) {
            packageEntity = this.find(pakage.getId());
        }
        packageEntity.setMemberEntities(this.update(pakage.getMembers()));
        return this.packageDao.save(packageEntity);
    }

    private List<MemberEntity> update(List<Member> members) {
        for (Member member : members) {
            member.accept(this);
        }
        return new ArrayList<>(this.memberEntities);
    }

    PackageEntity find(String id) {
        Optional<PackageEntity> packageEntity = this.packageDao.findById(id);
        if (packageEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return packageEntity.get();
    }

    @Override
    public void visit(Account account) {

    }

    @Override
    public void visit(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        if (pakage.getId() != null) {
            packageEntity = this.find(pakage.getId());
        }
        this.memberEntities.add(packageEntity);
        for (Member member : pakage.getMembers()) {
            member.accept(this);
        }
        MemberEntity memberEntity = this.memberEntities.peek();
        while (!packageEntity.equals(memberEntity)) {
            packageEntity.add(this.memberEntities.pop());
            memberEntity = this.memberEntities.peek();
        }
        this.packageDao.save(packageEntity);
    }

    @Override
    public void visit(Class clazz) {
        if (clazz.getId() == null) {
            this.createClassEntity(clazz);
        } else {
            Optional<ClassEntity> optionalClassEntity = this.classDao.findById(clazz.getId());
            if (optionalClassEntity.isEmpty()) {
                this.createClassEntity(clazz);
            } else {
                this.memberEntities.add(optionalClassEntity.get());
            }
        }
    }

    private void createClassEntity(Class clazz) {
        ClassEntity classEntity = new ClassEntity(clazz);
        classEntity = this.classDao.save(classEntity);
        this.memberEntities.add(classEntity);
    }

}
