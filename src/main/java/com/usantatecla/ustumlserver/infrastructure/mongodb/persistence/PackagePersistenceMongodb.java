package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PackagePersistenceMongodb implements PackagePersistence {

    private PackageDao packageDao;

    @Autowired
    public PackagePersistenceMongodb(PackageDao packageDao) {
        this.packageDao = packageDao;
    }

    @Override
    public Package read(String name) {
        return this.packageDao.findByName(name).toPackage();
    }

    @Override
    public void update(Package pakage) {
        PackageEntity packageEntity = this.packageDao.findByName(pakage.getName());
        this.packageDao.save(packageEntity);
    }

}
