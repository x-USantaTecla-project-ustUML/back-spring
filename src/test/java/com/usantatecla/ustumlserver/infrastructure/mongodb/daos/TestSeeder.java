package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Modifier;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ProjectEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestSeeder {

    public static String SESSION_ID = "test";
    public static Project PROJECT = Project.builder().id("id").name("project").members(new ArrayList<>()).build();
    public static Package PACKAGE = Package.builder().id("id").name("package").members(new ArrayList<>()).build();
    public static Class CLASS = Class.builder().id("id").name("class").modifiers(List.of(Modifier.PACKAGE))
            .attributes(new ArrayList<>()).methods(new ArrayList<>()).build();

    @Autowired
    private Seeder seeder;
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private PackageDao packageDao;
    @Autowired
    private ClassDao classDao;

    public void initialize() {
        this.seeder.initialize();
        this.sessionDao.save(SessionEntity.builder().id("id").sessionId(TestSeeder.SESSION_ID)
                .memberEntities(new ArrayList<>()).build());
        this.projectDao.save(new ProjectEntity(TestSeeder.PROJECT));
        this.packageDao.save(new PackageEntity(TestSeeder.PACKAGE));
        this.classDao.save(new ClassEntity(TestSeeder.CLASS));
    }

}
