package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class TestSeeder {

    public static String SESSION_ID = "test";

    public static Relation COMPOSITION = Composition.builder().id("id").target(TestSeeder.CLASS).build();
    public static Relation AGGREGATION = Aggregation.builder().id("id").target(TestSeeder.CLASS).build();
    public static Relation ASSOCIATION = Association.builder().id("id").target(TestSeeder.CLASS).build();
    public static Relation INHERITANCE = Inheritance.builder().id("id").target(TestSeeder.CLASS).build();
    public static Relation USE = Use.builder().id("id").target(TestSeeder.CLASS).build();
    public static Interface INTERFACE = Interface.builder().id("id").name("interface").modifiers(List.of(Modifier.PACKAGE))
        .attributes(new ArrayList<>()).methods(new ArrayList<>())
        .relations(new ArrayList<>()).build();
    public static Enum ENUM = Enum.builder().id("id").name("enum").modifiers(List.of(Modifier.PACKAGE))
            .attributes(new ArrayList<>()).methods(new ArrayList<>())
            .relations(new ArrayList<>()).build();
    public static Class CLASS = Class.builder().id("id").name("class").modifiers(List.of(Modifier.PACKAGE))
            .attributes(new ArrayList<>()).methods(new ArrayList<>())
            .relations(new ArrayList<>()).build();
    public static Package PACKAGE = Package.builder().id("id").name("pakageName")
            .members(Arrays.asList(TestSeeder.CLASS, TestSeeder.INTERFACE, TestSeeder.ENUM))
            .relations(new ArrayList<>()).build();
    public static Project PROJECT = Project.builder().id("id").name("project")
            .members(List.of(TestSeeder.PACKAGE)).relations(new ArrayList<>()).build();
    public static Account ACCOUNT = Account.builder().id("id").name("account")
            .projects(Collections.singletonList(TestSeeder.PROJECT)).build();

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
    @Autowired
    private InterfaceDao interfaceDao;
    @Autowired
    private EnumDao enumDao;
    @Autowired
    private UseDao useDao;
    @Autowired
    private InheritanceDao inheritanceDao;
    @Autowired
    private CompositionDao compositionDao;
    @Autowired
    private AggregationDao aggregationDao;
    @Autowired
    private AssociationDao associationDao;

    public void initialize() {
        this.seeder.initialize();
        this.sessionDao.save(SessionEntity.builder().id("id").sessionId(TestSeeder.SESSION_ID)
                .memberEntities(new ArrayList<>()).build());
        this.classDao.save(new ClassEntity(TestSeeder.CLASS));
        this.interfaceDao.save(new InterfaceEntity(TestSeeder.INTERFACE));
        this.enumDao.save(new EnumEntity(TestSeeder.ENUM));
        this.packageDao.save(new PackageEntity(TestSeeder.PACKAGE));
        this.projectDao.save(new ProjectEntity(TestSeeder.PROJECT));
        this.accountDao.save(new AccountEntity(TestSeeder.ACCOUNT));
    }

}
