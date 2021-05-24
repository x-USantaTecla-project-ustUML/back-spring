package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class Seeder {

    public static String PROJECT_ID = "project123";

    private PackageDao packageDao;
    private ClassDao classDao;
    private UserDao userDao;
    private SessionDao sessionDao;

    @Autowired
    public  Seeder(PackageDao packageDao, ClassDao classDao, UserDao userDao, SessionDao sessionDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.userDao = userDao;
        this.sessionDao = sessionDao;
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
        this.sessionDao.deleteAll();
    }

    private void seed() {
        this.packageDao.save(PackageEntity.builder().id(Seeder.PROJECT_ID).name("name").build());
        String pass = new BCryptPasswordEncoder().encode("a");
        this.userDao.save(UserEntity.builder().email("a").password(pass).role(Role.AUTHENTICATED).build());
    }

}
