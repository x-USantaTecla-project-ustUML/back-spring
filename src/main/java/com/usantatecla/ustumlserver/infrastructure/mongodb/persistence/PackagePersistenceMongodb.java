package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.domain.services.CommandParserException;
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

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;
    private ClassDao classDao;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao, ClassDao classDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
    }

    @Override
    public Package read(String name) {
        return this.find(name).toPackage();
    }

    private PackageEntity find(String name) {
        PackageEntity packageEntity = this.packageDao.findByName(name);
        if (packageEntity == null) {
            throw new CommandParserException(Error.MEMBER_NOT_FOUND, name);
        }
        return packageEntity;
    }

    @Override
    public void update(Package pakage) {
        this.save(pakage, this.find(pakage.getName()));
    }

    public void save(Package pakage, PackageEntity packageEntity){
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : pakage.getMembers()) {
            MemberAcceptor memberAcceptor = new MemberAcceptor(this);
            member.accept(memberAcceptor);
            memberEntities.add(memberAcceptor.get());
        }
        packageEntity.setMemberEntities(memberEntities);
        this.packageDao.save(packageEntity);
    }

    public void save(ClassEntity classEntity){
        this.classDao.save(classEntity);
    }

}
