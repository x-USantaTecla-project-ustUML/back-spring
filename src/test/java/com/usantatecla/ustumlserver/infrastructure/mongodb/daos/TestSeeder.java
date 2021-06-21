package com.usantatecla.ustumlserver.infrastructure.mongodb.daos;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.domain.model.builders.AttributeBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.MethodBuilder;
import com.usantatecla.ustumlserver.domain.model.classDiagram.*;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.relations.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ProjectEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.EnumEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.InterfaceEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestSeeder {

    public static String SESSION_ID = "test";

    public static Interface INTERFACE;
    public static Enum ENUM;
    public static Class CLASS;
    public static Class ABSTRACT_CLASS;
    public static Package PACKAGE;
    public static Project PROJECT;
    public static Account ACCOUNT;
    public static Relation COMPOSITION;
    public static Relation AGGREGATION;
    public static Relation ASSOCIATION;
    public static Relation INHERITANCE;
    public static Relation USE;
    public static Relation ABSTRACT_CLASS_RELATION;

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
        this.deleteAll();
        this.seeder.initialize();
        this.initializeStaticVariables();
        this.sessionDao.save(SessionEntity.builder().id("id").sessionId(TestSeeder.SESSION_ID)
                .memberEntities(new ArrayList<>()).build());
        this.classDao.save(new ClassEntity(TestSeeder.CLASS));
        this.interfaceDao.save(new InterfaceEntity(TestSeeder.INTERFACE));
        this.enumDao.save(new EnumEntity(TestSeeder.ENUM));
        this.packageDao.save(new PackageEntity(TestSeeder.PACKAGE));
        this.projectDao.save(new ProjectEntity(TestSeeder.PROJECT));
        this.accountDao.save(new AccountEntity(TestSeeder.ACCOUNT));
    }

    public void initializeStaticVariables() {
        TestSeeder.INTERFACE = Interface.builder().id("id").name("interface").modifiers(List.of(Modifier.PACKAGE))
                .attributes(new ArrayList<>()).methods(new ArrayList<>())
                .relations(new ArrayList<>()).build();
        TestSeeder.ENUM = Enum.builder().id("id").name("enum").modifiers(List.of(Modifier.PACKAGE))
                .attributes(new ArrayList<>()).methods(new ArrayList<>())
                .relations(new ArrayList<>()).build();
        TestSeeder.CLASS = Class.builder().id("id").name("class").modifiers(List.of(Modifier.PACKAGE))
                .attributes(new ArrayList<>()).methods(new ArrayList<>())
                .relations(new ArrayList<>()).build();
        TestSeeder.ABSTRACT_CLASS = Class.builder().id("id2").name("abstract_class")
                .modifiers(new ArrayList<>(List.of(Modifier.PACKAGE, Modifier.ABSTRACT)))
                .attributes(new ArrayList<>(List.of(
                        new AttributeBuilder().name("attribute").type("int").modifiers(Modifier.PRIVATE).build()
                ))).methods(new ArrayList<>(List.of(
                        new MethodBuilder().name("method").type("String")
                                .modifiers(Modifier.PRIVATE)
                                .parameters(new Parameter("param1", "int"), new Parameter("param2", "String"))
                                .build()
                )))
                .relations(new ArrayList<>()).build();
        TestSeeder.PACKAGE = Package.builder().id("id").name("pakageName")
                .members(new ArrayList<>(List.of(TestSeeder.CLASS, TestSeeder.ENUM)))
                .relations(new ArrayList<>()).build();
        TestSeeder.PROJECT = Project.builder().id("id").name("project")
                .members(new ArrayList<>(List.of(TestSeeder.PACKAGE, TestSeeder.INTERFACE)))
                .relations(new ArrayList<>()).build();
        TestSeeder.ACCOUNT = Account.builder().id("id").name("account").role(Role.AUTHENTICATED)
                .projects(new ArrayList<>(List.of(TestSeeder.PROJECT))).build();
        TestSeeder.COMPOSITION = Composition.builder().id("id")
                .target(TestSeeder.CLASS).build();
        TestSeeder.AGGREGATION = Aggregation.builder().id("id")
                .target(TestSeeder.CLASS).build();
        TestSeeder.ASSOCIATION = Association.builder().id("id")
                .target(TestSeeder.PROJECT).build();
        TestSeeder.INHERITANCE = Inheritance.builder().id("id")
                .target(TestSeeder.INTERFACE).build();
        TestSeeder.USE = Use.builder().id("id")
                .target(TestSeeder.ENUM).build();
        TestSeeder.ABSTRACT_CLASS_RELATION = Use.builder().id("id2")
                .target(TestSeeder.INTERFACE).build();
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
        TestSeeder.ENUM.setRelations(new ArrayList<>(List.of(TestSeeder.COMPOSITION)));
        TestSeeder.CLASS.setRelations(new ArrayList<>(List.of(TestSeeder.USE, TestSeeder.AGGREGATION)));
        TestSeeder.PACKAGE.setRelations(new ArrayList<>(List.of(TestSeeder.ASSOCIATION, TestSeeder.INHERITANCE)));
        this.enumDao.save(new EnumEntity(TestSeeder.ENUM));
        this.classDao.save(new ClassEntity(TestSeeder.CLASS));
        this.packageDao.save(new PackageEntity(TestSeeder.PACKAGE));
    }

    public void seedAbstractClassWithRelation() {
        this.useDao.save(new UseEntity((Use) TestSeeder.ABSTRACT_CLASS_RELATION,
                this.memberEntityFinder.find(TestSeeder.ABSTRACT_CLASS_RELATION.getTarget())));
        TestSeeder.ABSTRACT_CLASS.setRelations(new ArrayList<>(List.of(TestSeeder.ABSTRACT_CLASS_RELATION)));
        TestSeeder.PROJECT.add(TestSeeder.ABSTRACT_CLASS);
        this.classDao.save(new ClassEntity(TestSeeder.ABSTRACT_CLASS));
        this.projectDao.save(new ProjectEntity(TestSeeder.PROJECT));
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
