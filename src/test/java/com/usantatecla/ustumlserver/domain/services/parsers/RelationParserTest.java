package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.builders.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RelationParserTest {

    private RelationParser relationParser;

    @BeforeEach
    void beforeEach() {
        this.relationParser = new RelationParser();
    }

    @Test
    void testGivenUseParserWhenGetNotExistentTargetThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "use: \"notExist\"" +
                "}").build();
        assertThrows(ParserException.class, () -> this.relationParser.get(Seeder.ACCOUNT, command));
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenProjectsThenReturn() {
        String name = "project";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + name + "\"" +
                "}").build();
        Project origin = new ProjectBuilder().build();
        Project target = new ProjectBuilder().name(name).build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(origin, target)
                .build();
        Relation expected = new RelationBuilder().use().target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenPackagesThenReturn() {
        String projectName = "project";
        String targetName = "target";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + projectName + "." + targetName + "\"" +
                "}").build();
        Package origin = new PackageBuilder().build();
        Package target = new PackageBuilder().name(targetName).build();
        Project project = new ProjectBuilder().name(projectName)
                .packages(origin, target)
                .build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(project)
                .build();
        Relation expected = new RelationBuilder().use().target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenClassesThenReturn() {
        String projectName = "project";
        String targetName = "target";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + projectName + "." + targetName + "\"" +
                "}").build();
        Class origin = new ClassBuilder().build();
        Class target = new ClassBuilder().name(targetName).build();
        Project project = new ProjectBuilder().name(projectName)
                .classes(origin, target)
                .build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(project)
                .build();
        Relation expected = new RelationBuilder().use().target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenDifferentDepthThenReturn() {
        String projectName = "project";
        String packageName = "pakage";
        String targetName = "target";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + projectName + "." + packageName + "." + targetName + "\"" +
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
        Relation expected = new RelationBuilder().use().target(target).build();
        assertThat(this.relationParser.get(account, command), is(expected));
    }

}
