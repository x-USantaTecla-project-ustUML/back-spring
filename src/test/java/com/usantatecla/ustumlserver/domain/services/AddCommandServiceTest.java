package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfig
class AddCommandServiceTest {

    @Autowired
    private TestSeeder testSeeder;
    @Mock
    private SessionService sessionService;
    @Autowired
    @InjectMocks
    private CommandService commandService;

    @Test
    void testGivenCommandServiceWhenAccountExecuteAddProjectThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               project: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Account expected = new AccountBuilder(Seeder.ACCOUNT)
                .project().name(name)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(Seeder.ACCOUNT).build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteAddNotProjectThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               class: a" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteAddExistentProjectThenThrowException() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               project: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .project().name(name)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(account));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteAddMembersThenReturn() {
        String packageName = "a";
        String className = "b";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               package: " + packageName +
                "           }," +
                "           {" +
                "               class: " + className +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Project expected = new ProjectBuilder()
                .pakage().name(packageName)
                .clazz().name(className)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ProjectBuilder().build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteAddProjectThenThrowException() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               project: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ProjectBuilder().build()));
        assertThrows(ParserException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteAddExistentMemberThenThrowException() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               class: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Project project = new ProjectBuilder()
                .clazz().name(name)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, project));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteAddMembersThenReturn() {
        String packageName = "a";
        String className = "b";
        String interfaceName = "c";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               package: " + packageName +
                "           }," +
                "           {" +
                "               class: " + className +
                "           }," +
                "           {" +
                "               interface: " + interfaceName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder()
                .pakage().name(packageName)
                .clazz().name(className)
                ._interface().name(interfaceName)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new PackageBuilder().build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenClassExecuteAddMembersThenReturn() {
        String attribute = "\"public int x\"";
        String method = "\"private string y()\"";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               member: " + attribute + "},{" +
                "               member: " + method +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Class expected = new ClassBuilder().attribute()._public().type("int").name("x")
                .method()._private().type("string").name("y").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ClassBuilder().build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenClassExecuteAddModifiersThenThrowException() {
        String oldModifiers = "\"package\"";
        String newModifiers = "\"abstract\"";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       modifiers: " + oldModifiers + "," +
                "       set: " + newModifiers +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ClassBuilder().build()));
        assertThrows(ParserException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteAddProjectThenThrowException() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               project: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new PackageBuilder().build()));
        assertThrows(ParserException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteAddExistentMemberThenThrowException() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               class: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package pakage = new PackageBuilder()
                .clazz().name(name)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, pakage));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteAddMemberWithRelationThenReturn() {
        this.testSeeder.initialize();
        String originName = "a";
        String targetName = TestSeeder.PROJECT.getName();
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               project: " + originName + "," +
                "               relations: [" +
                "                   {" +
                "                       use:" + targetName +
                "                   }" +
                "               ]" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Project target = new ProjectBuilder(TestSeeder.PROJECT).build();
        Project origin = new ProjectBuilder().name(originName).use().target(target).role("").route(targetName).build();
        Account expected = new AccountBuilder(TestSeeder.ACCOUNT)
                .projects(origin).build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build()));
        Member actual = this.commandService.execute(command, CommandServiceTest.SESSION_ID);
        assertThat(actual, is(expected));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteAddMemberWithBadRelationThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               project: \"pro\"," +
                "               relations: [" +
                "                   {" +
                "                       use: \"none\"" +
                "                   }" +
                "               ]" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(Seeder.ACCOUNT).build()));
        assertThrows(ParserException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

}
