package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.builders.*;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UseParserTest {

    @Mock
    private AccountPersistence accountPersistence;

    @Spy
    private UseParser useParser;

    @BeforeEach
    void beforeEach() {
        doReturn(Seeder.ACCOUNT.getEmail()).when(this.useParser).getAuthenticatedEmail();
    }

    @Disabled
    @Test
    void testGivenUseParserWhenGetTargetOfAnotherAccountThenThrowException() {
        // TODO
    }

    @Test
    void testGivenUseParserWhenGetNotExistentTargetThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                    "use: \"" + Seeder.ACCOUNT.getEmail() + "/notExist\"" +
                "}").build();

        when(this.accountPersistence.read(anyString())).thenReturn(Seeder.ACCOUNT);
        assertThrows(ParserException.class, () -> this.useParser.get(command, new ProjectBuilder().build(), this.accountPersistence));
    }

    @Disabled
    @Test
    void testGivenUseParserWhenGetTargetWithoutAbsoluteRouteThen() {
        // TODO
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenProjectsThenReturn() {
        String name = "project";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + Seeder.ACCOUNT.getEmail() + "/" + name + "\"" +
                "}").build();
        Project origin = new ProjectBuilder().build();
        Project target = new ProjectBuilder().name(name).build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(origin, target)
                .build();
        Relation expected = new RelationBuilder().use().target(target).build();
        when(this.accountPersistence.read(anyString())).thenReturn(account);
        assertThat(this.useParser.get(command, origin, this.accountPersistence), is(expected));
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenPackagesThenReturn() {
        String projectName = "project";
        String targetName = "target";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + Seeder.ACCOUNT.getEmail() + "/" + projectName + "/" + targetName + "\"" +
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
        when(this.accountPersistence.read(anyString())).thenReturn(account);
        assertThat(this.useParser.get(command, origin, this.accountPersistence), is(expected));
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenClassesThenReturn() {
        String projectName = "project";
        String targetName = "target";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + Seeder.ACCOUNT.getEmail() + "/" + projectName + "/" + targetName + "\"" +
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
        when(this.accountPersistence.read(anyString())).thenReturn(account);
        assertThat(this.useParser.get(command, origin, this.accountPersistence), is(expected));
    }

    @Test
    void testGivenUseParserWhenGetRelationBetweenDifferentDepthThenReturn() {
        String projectName = "project";
        String packageName = "pakage";
        String targetName = "target";
        Command command = new CommandBuilder().command("{" +
                "use: \"" + Seeder.ACCOUNT.getEmail() + "/" + projectName + "/" + packageName + "/" + targetName + "\"" +
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
        when(this.accountPersistence.read(anyString())).thenReturn(account);
        assertThat(this.useParser.get(command, origin, this.accountPersistence), is(expected));
    }

}
