package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.MemberEntityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestSeeder {

    public static String SESSION_ID = "test";

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
            .members(new ArrayList<>(List.of(TestSeeder.CLASS, TestSeeder.ENUM)))
            .relations(new ArrayList<>()).build();
    public static Project PROJECT = Project.builder().id("id").name("project")
            .members(new ArrayList<>(List.of(TestSeeder.PACKAGE, TestSeeder.INTERFACE))).relations(new ArrayList<>()).build();
    public static Account ACCOUNT = Account.builder().id("id").name("account").role(Role.AUTHENTICATED)
            .projects(new ArrayList<>(List.of(TestSeeder.PROJECT))).build();
    public static Relation COMPOSITION = Composition.builder().id("id")
            .target(TestSeeder.CLASS).build();
    public static Relation AGGREGATION = Aggregation.builder().id("id")
            .target(TestSeeder.CLASS).build();
    public static Relation ASSOCIATION = Association.builder().id("id")
            .target(TestSeeder.PROJECT).build();
    public static Relation INHERITANCE = Inheritance.builder().id("id")
            .target(TestSeeder.INTERFACE).build();
    public static Relation USE = Use.builder().id("id")
            .target(TestSeeder.ENUM).build();

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
    @Autowired
    private MemberEntityFinder memberEntityFinder;

    public void initialize() {
        this.seeder.initialize();
        this.deleteAll();
        this.sessionDao.save(SessionEntity.builder().id("id").sessionId(TestSeeder.SESSION_ID)
                .memberEntities(new ArrayList<>()).build());
        this.classDao.save(new ClassEntity(TestSeeder.CLASS));
        this.interfaceDao.save(new InterfaceEntity(TestSeeder.INTERFACE));
        this.enumDao.save(new EnumEntity(TestSeeder.ENUM));
        this.packageDao.save(new PackageEntity(TestSeeder.PACKAGE));
        this.projectDao.save(new ProjectEntity(TestSeeder.PROJECT));
        this.accountDao.save(new AccountEntity(TestSeeder.ACCOUNT));
    }

    public void seedRelations() {
        this.compositionDao.save(new CompositionEntity((Composition) TestSeeder.COMPOSITION,
                this.memberEntityFinder.find(TestSeeder.COMPOSITION.getTarget())));
        this.useDao.save(new UseEntity((Use) TestSeeder.USE,
                this.memberEntityFinder.find(TestSeeder.USE.getTarget())));
        this.aggregationDao.save(new AggregationEntity((Aggregation) TestSeeder.AGGREGATION,
                this.memberEntityFinder.find(TestSeeder.AGGREGATION.getTarget())));
        this.associationDao.save(new AssociationEntity((Association) TestSeeder.ASSOCIATION,
                this.memberEntityFinder.find(TestSeeder.ASSOCIATION.getTarget())));
        this.inheritanceDao.save(new InheritanceEntity((Inheritance) TestSeeder.INHERITANCE,
                this.memberEntityFinder.find(TestSeeder.INHERITANCE.getTarget())));
        TestSeeder.ENUM.add(TestSeeder.COMPOSITION);
        TestSeeder.CLASS.add(TestSeeder.USE);
        TestSeeder.CLASS.add(TestSeeder.AGGREGATION);
        TestSeeder.PACKAGE.add(TestSeeder.ASSOCIATION);
        TestSeeder.PACKAGE.add(TestSeeder.INHERITANCE);
        this.enumDao.save(new EnumEntity(TestSeeder.ENUM));
        this.classDao.save(new ClassEntity(TestSeeder.CLASS));
        this.packageDao.save(new PackageEntity(TestSeeder.PACKAGE));
    }

    private void deleteAll() {
        this.enumDao.deleteAll();
        this.interfaceDao.deleteAll();
        this.projectDao.deleteAll();
        this.compositionDao.deleteAll();
        this.useDao.deleteAll();
        this.aggregationDao.deleteAll();
        this.associationDao.deleteAll();
        this.inheritanceDao.deleteAll();
    }

}
