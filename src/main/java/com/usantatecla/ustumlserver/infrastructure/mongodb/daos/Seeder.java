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
        this.initialize();
    }

    public void initialize() {
        this.deleteAll();
        this.seed();
    }

    private void deleteAll() {
        this.packageDao.deleteAll();
    }

    private void seed() {
        this.packageDao.save(PackageEntity.builder().name("name").build());
    }

}
