package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;
    private ClassDao classDao;
    private MemberEntity memberEntity;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao, ClassDao classDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
    }

    @Override
    public Package read(String name) {
        return this.find(name).toPackage();
    }

    @Override
    public void update(Package pakage) {
        PackageEntity packageEntity = this.find(pakage.getName());
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : pakage.getMembers()) {
            member.accept(this);
            memberEntities.add(this.memberEntity);
        }
        packageEntity.setMemberEntities(memberEntities);
        this.packageDao.save(packageEntity);
    }

    @Override
    public void visit(Package pakage) {

    }

    @Override
    public void visit(Class clazz) {
        this.memberEntity = new ClassEntity(clazz);
        this.classDao.save((ClassEntity) this.memberEntity);
    }

}
