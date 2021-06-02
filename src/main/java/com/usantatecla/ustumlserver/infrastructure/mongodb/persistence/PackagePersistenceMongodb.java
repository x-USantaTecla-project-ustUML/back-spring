package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageUpdater packageUpdater;

    @Autowired
    public PackagePersistenceMongodb(PackageUpdater packageUpdater) {
        this.packageUpdater = packageUpdater;
    }

    @Override
    public Package read(String id) {
        Package pakage = this.packageUpdater.find(id).toPackage();
        System.out.println(pakage);
        return pakage;
    }

    @Override
    public void update(Package pakage) {
        this.packageUpdater.update(pakage);
    }

}
