package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import com.usantatecla.ustumlserver.domain.services.Error;
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
            throw new CommandParserException(Error.MEMBER_NOT_FOUND, id);
        }
        return packageEntity.get();
    }

    @Override
    public void update(Package pakage) {
        PackageEntity packageEntity = this.find(pakage.getId());
        for (Member member : pakage.getMembers()) {
            member.accept(this);
        }
        List<MemberEntity> memberEntities = new ArrayList<>();
        while (!this.memberEntities.empty()) {
            memberEntities.add(this.memberEntities.pop());
        }
        packageEntity.setMemberEntities(memberEntities);
        this.packageDao.save(packageEntity);
    }

    @Override
    public void visit(Package pakage) {
        Optional<PackageEntity> packageEntityDB = this.packageDao.findById(pakage.getId());
        PackageEntity packageEntity;
        if(packageEntityDB.isEmpty()) {
            packageEntity = new PackageEntity(pakage);
        } else {
            packageEntity = packageEntityDB.get();
        }
        this.memberEntities.add(packageEntity);
        for (Member member : pakage.getMembers()) {
            member.accept(this);
        }
        List<MemberEntity> memberEntities = new ArrayList<>();
        MemberEntity memberEntity = this.memberEntities.peek();
        while (!packageEntity.equals(memberEntity)) {
            memberEntities.add(this.memberEntities.pop());
            memberEntity = this.memberEntities.peek();
        }
        packageEntity.setMemberEntities(memberEntities);
        this.packageDao.save(packageEntity);
    }

    @Override
    public void visit(Class clazz) {
        Optional<ClassEntity> classEntity = this.classDao.findById(clazz.getId());
        if(classEntity.isEmpty()) {
            this.classDao.save(new ClassEntity(clazz));
        } else {
            this.memberEntities.add(classEntity.get());
        }
    }

}
