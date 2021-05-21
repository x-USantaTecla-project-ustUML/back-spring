package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class Seeder {

    private PackageDao packageDao;
    private ClassDao classDao;
    private UserDao userDao;

    @Autowired
    public  Seeder(PackageDao packageDao, ClassDao classDao, UserDao userDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.userDao = userDao;
        this.initialize();
    }

    public void initialize() {
        this.deleteAll();
        this.seed();
    }

    private void deleteAll() {
        this.packageDao.deleteAll();
        this.classDao.deleteAll();
        this.userDao.deleteAll();
    }

    private void seed() {
        this.packageDao.save(PackageEntity.builder().name("name").build());
        String pass = new BCryptPasswordEncoder().encode("a");
        this.userDao.save(UserEntity.builder().email("a").password(pass).role(Role.AUTHENTICATED).build());
    }

}
