package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;


public class MemberAcceptor {

    private MemberEntity memberEntity;
    private PackagePersistenceMongodb packagePersistenceMongodb;

    MemberAcceptor(PackagePersistenceMongodb packagePersistenceMongodb){
        this.packagePersistenceMongodb = packagePersistenceMongodb;
    }

    public void visit(Package pakage) {
        this.memberEntity = new PackageEntity(pakage);
        this.packagePersistenceMongodb.save(pakage, (PackageEntity) this.memberEntity);
    }

    public void visit(Class clazz) {
        this.memberEntity = new ClassEntity(clazz);
        this.packagePersistenceMongodb.save((ClassEntity) this.memberEntity);
    }

    MemberEntity get(){
        return this.memberEntity;
    }

}
