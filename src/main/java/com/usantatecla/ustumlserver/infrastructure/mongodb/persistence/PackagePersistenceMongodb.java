package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Stack;

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;
    private ClassDao classDao;
    private Stack<MemberEntity> memberEntities;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao, ClassDao classDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.memberEntities = new Stack<>();
    }

    @Override
    public Package read(String id) {
        return this.find(id).toPackage();
    }

    private PackageEntity find(String id) {
        Optional<PackageEntity> packageEntity = this.packageDao.findById(id);
        if (packageEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return packageEntity.get();
    }

    @Override
    public void update(Package pakage) {
        PackageEntity packageEntity = this.find(pakage.getId());
        for (Member member : pakage.getMembers()) {
            member.accept(this);
        }
        while (!this.memberEntities.empty()) {
            packageEntity.add(this.memberEntities.pop());
        }
        this.packageDao.save(packageEntity);
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
            ClassEntity classEntity = new ClassEntity(clazz);
            assert classEntity == this.classDao.save(classEntity);
            this.memberEntities.add(classEntity);
        } else {
            Optional<ClassEntity> classEntity = this.classDao.findById(clazz.getId());
            if (classEntity.isEmpty()) {
                this.memberEntities.add(this.classDao.save(new ClassEntity(clazz)));
            } else {
                this.memberEntities.add(classEntity.get());
            }
        }
    }

}
