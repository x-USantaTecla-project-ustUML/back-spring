package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private MemberUpdater memberUpdater;

    @Autowired
    public PackagePersistenceMongodb(MemberUpdater memberUpdater) {
        this.memberUpdater = memberUpdater;
    }

    @Override
    public Package read(String id) {
        return this.memberUpdater.find(id).toPackage();
    }

    @Override
    public void update(Package pakage) {
        this.memberUpdater.update(pakage);
    }

}
