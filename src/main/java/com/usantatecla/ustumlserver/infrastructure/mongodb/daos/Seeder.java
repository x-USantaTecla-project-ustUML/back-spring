package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class Seeder {

    public static final String PROJECT_ID = "project123";
    public static final Account ACCOUNT = Account.builder().id("a").name("a").email("a")
            .password(new BCryptPasswordEncoder().encode("a"))
            .role(Role.AUTHENTICATED)
            .projects(new ArrayList<>()).build();

    private PackageDao packageDao;
    private ClassDao classDao;
    private AccountDao accountDao;
    private SessionDao sessionDao;

    @Autowired
    public  Seeder(PackageDao packageDao, ClassDao classDao, AccountDao accountDao, SessionDao sessionDao) {
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.accountDao = accountDao;
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
        this.accountDao.deleteAll();
        this.sessionDao.deleteAll();
    }

    private void seed() {
        this.packageDao.save(PackageEntity.builder().id(Seeder.PROJECT_ID).name("name").build());
        this.accountDao.save(new AccountEntity(Seeder.ACCOUNT));
    }

}
