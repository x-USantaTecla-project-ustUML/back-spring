package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Seeder {

    private PackageDao packageDao;

    @Autowired
    public  Seeder(PackageDao packageDao) {
        this.packageDao = packageDao;
        this.deleteAll();
        this.initialize();
    }

    private void deleteAll() {
        this.packageDao.deleteAll();
    }

    private void initialize() {
        this.packageDao.save(PackageEntity.builder().name("name").build());
    }

}
