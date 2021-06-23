package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.*;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class RelationParserTest {

    protected static final String USE = "use: ";
    protected static final String COMPOSITION = "composition: ";
    protected static final String ASSOCIATION = "association: ";
    protected static final String AGGREGATION = "aggregation: ";
    protected static final String INHERITANCE = "inheritance: ";

    private RelationParser relationParser;
    private String relationType;

    protected abstract String setRelationType();
    protected abstract RelationBuilder createBuilderWithRelation();

    @BeforeEach
    void beforeEach() {
        this.relationParser = new RelationParser();
        this.relationType = this.setRelationType();
    }

    @Test
    void testGivenRelationParserWhenGetNotExistentTargetThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"notExist\"" +
                "}").build();
        assertThrows(ParserException.class, () -> this.relationParser.get(Seeder.ACCOUNT, command));
    }

    @Test
    void testGivenRelationParserWhenGetRelationBetweenProjectsThenReturn() {
        String name = "project";
        String role = "role";
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"" + name + "\", " +
                "role: " + role +
                "}").build();
        Project origin = new ProjectBuilder().build();
        Project target = new ProjectBuilder().name(name).build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(origin, target)
                .build();
        Relation expected = this.createBuilderWithRelation().route(name).role(role).target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

    @Test
    void testGivenRelationParserWhenGetRelationBetweenPackagesThenReturn() {
        String projectName = "project";
        String targetName = "target";
        String route = projectName + "." + targetName;
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"" + route + "\"" +
                "}").build();
        Package origin = new PackageBuilder().build();
        Package target = new PackageBuilder().name(targetName).build();
        Project project = new ProjectBuilder().name(projectName)
                .packages(origin, target)
                .build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(project)
                .build();
        Relation expected = this.createBuilderWithRelation().route(route).role("").target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

    @Test
    void testGivenRelationParserWhenGetRelationBetweenClassesThenReturn() {
        String projectName = "project";
        String targetName = "target";
        String route = projectName + "." + targetName;
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"" + route + "\"" +
                "}").build();
        Class origin = new ClassBuilder().build();
        Class target = new ClassBuilder().name(targetName).build();
        Project project = new ProjectBuilder().name(projectName)
                .classes(origin, target)
                .build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(project)
                .build();
        Relation expected = this.createBuilderWithRelation().route(route).role("").target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

    @Test
    void testGivenRelationParserWhenGetRelationBetweenDifferentDepthThenReturn() {
        String projectName = "project";
        String packageName = "pakage";
        String targetName = "target";
        String route = projectName + "." + packageName + "." + targetName;
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"" + projectName + "." + packageName + "." + targetName + "\"" +
                "}").build();
        Package origin = new PackageBuilder().build();
        Class target = new ClassBuilder().name(targetName).build();
        Package pakage = new PackageBuilder().name(packageName)
                .classes(target)
                .build();
        Project project = new ProjectBuilder().name(projectName)
                .packages(origin, pakage)
                .build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(project)
                .build();
        Relation expected = this.createBuilderWithRelation().route(route).role("").target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

    @Test
    void testGivenRelationParserWhenGetModifiedRelationWithoutSetKeyThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"something\", " +
                "role: \"something\"" +
                "}").build();
        assertThrows(ParserException.class, () -> this.relationParser.getModifiedRelation(Seeder.ACCOUNT, command));
    }

    @Test
    void testGivenRelationParserWhenGetModifiedRelationWithoutSetValueThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"something\", " +
                "set: \"\", " +
                "role: \"something\"" +
                "}").build();
        assertThrows(ParserException.class, () -> this.relationParser.getModifiedRelation(Seeder.ACCOUNT, command));
    }

    @Test
    void testGivenRelationParserWhenGetModifiedRelationNotExistentTargetThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                this.relationType + "\"something\", " +
                "set: notExist\"" +
                "}").build();
        assertThrows(ParserException.class, () -> this.relationParser.getModifiedRelation(Seeder.ACCOUNT, command));
    }

}
