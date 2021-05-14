package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberAcceptor {

    private MemberEntity memberEntity;
    private PackagePersistenceMongodb packagePersistenceMongodb;

    MemberAcceptor(PackagePersistenceMongodb packagePersistenceMongodb){
        this.packagePersistenceMongodb = packagePersistenceMongodb;
    }

    public void visit(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : pakage.getMembers()) {
            MemberAcceptor memberAcceptor = new MemberAcceptor(this.packagePersistenceMongodb);
            member.accept(memberAcceptor);
            memberEntities.add(memberAcceptor.get());
        }
        packageEntity.setMemberEntities(memberEntities);
        this.memberEntity = packageEntity;
        this.packagePersistenceMongodb.save((PackageEntity) this.memberEntity);
    }

    public void visit(Class clazz) {
        this.memberEntity = new ClassEntity(clazz);
        this.packagePersistenceMongodb.save((ClassEntity) this.memberEntity);
    }

    MemberEntity get(){
        return this.memberEntity;
    }

}
