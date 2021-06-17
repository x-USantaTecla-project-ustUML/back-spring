package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
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
class ModifyCommandServiceTest {
    
    @Mock
    protected SessionService sessionService;
    @Autowired
    @InjectMocks
    protected CommandService commandService;

    @Test
    void testGivenCommandServiceWhenAccountExecuteModifyProjectThenReturn() {
        String oldName = "a";
        String newName = "b";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               project: " + oldName + "," +
                "               set: " + newName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .project().name(oldName)
                .build();
        Account expected = new AccountBuilder(Seeder.ACCOUNT)
                .project().name(newName)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(account));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteModifyNotExistentProjectThenThrowException() {
        String existentProject = "a";
        String notExistentProject = "b";
        String newName = "c";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               project: " + notExistentProject + "," +
                "               set: " + newName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .project().name(existentProject)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(account));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteModifyMembersThenReturn() {
        String oldPackageName = "a";
        String oldClassName = "b";
        String newPackageName = "aa";
        String newClassName = "bb";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               package: " + oldPackageName + "," +
                "               set: " + newPackageName +
                "           }," +
                "           {" +
                "               class: " + oldClassName + "," +
                "               set: " + newClassName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Project project = new ProjectBuilder()
                .pakage().name(oldPackageName)
                .clazz().name(oldClassName)
                .build();
        Project expected = new ProjectBuilder()
                .pakage().name(newPackageName)
                .clazz().name(newClassName)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, project));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteModifyWithoutSetProjectThenThrowException() {
        String existentProject = "a";
        String notExistentProject = "b";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               project: " + notExistentProject +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .project().name(existentProject)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(account));
        assertThrows(ParserException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteModifyNonExistentMemberThenThrowException() {
        String oldName = "a";
        String newName = "b";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               class: " + oldName + "," +
                "               set: " + newName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Project project = new ProjectBuilder()
                .clazz().name(newName)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, project));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteModifyMembersThenReturn() {
        String packageName = "a";
        String className = "b";
        String interfaceName = "c";
        String newPackageName = "aa";
        String newClassName = "bb";
        String newInterfaceName = "cc";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               package: " + packageName + "," +
                "               set: " + newPackageName +
                "           }," +
                "           {" +
                "               class: " + className + "," +
                "               set: " + newClassName +
                "           }," +
                "           {" +
                "               interface: " + interfaceName + "," +
                "               set: " + newInterfaceName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package pakage = new PackageBuilder()
                .pakage().name(packageName)
                .clazz().name(className)
                ._interface().name(interfaceName)
                .build();
        Package expected = new PackageBuilder()
                .pakage().name(newPackageName)
                .clazz().name(newClassName)
                ._interface().name(newInterfaceName)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, pakage));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteModifyMembersAlreadyExistThenThrowException() {
        String packageName = "a";
        String interfaceName = "c";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               package: " + packageName + "," +
                "               set: " + interfaceName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package pakage = new PackageBuilder()
                .pakage().name(packageName)
                ._interface().name(interfaceName)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, pakage));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenClassExecuteModifyMembersThenReturn() {
        String attribute = "\"public int x\"";
        String method = "\"private string y()\"";
        String newAttribute = "\"public double j\"";
        String newMethod = "\"public int w()\"";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               member: " + attribute + ", " +
                "               set: " + newAttribute +
                "},{" +
                "               member: " + method + ", " +
                "               set: " + newMethod +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Class clazz = new ClassBuilder().attribute()._public().type("int").name("x")
                .method()._private().type("string").name("y").build();
        Class expected = new ClassBuilder().attribute()._public().type("double").name("j")
                .method()._public().type("int").name("w").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, clazz));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenClassExecuteModifyAttributeNotExistThenThrowException() {
        String attribute = "\"public int x\"";
        String newAttribute = "\"public double j\"";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [" +
                "           {" +
                "               member: " + attribute + ", " +
                "               set: " + newAttribute +
                "}" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ClassBuilder().build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenClassExecuteModifyMethodNotExistThenThrowException() {
        String method = "\"private string y()\"";
        String newMethod = "\"public int w()\"";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       members: [{" +
                "               member: " + method + ", " +
                "               set: " + newMethod +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ClassBuilder().build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenClassExecuteModifyModifiersThenReturn() {
        String oldModifiers = "\"public\"";
        String newModifiers = "\"abstract\"";
        Command command = new CommandBuilder().command("{" +
                "   modify: {" +
                "       modifiers: " + oldModifiers + ", " +
                "       set: " + newModifiers +
                "   }" +
                "}").build();
        Class clazz = new ClassBuilder()._public().build();
        Class expected = new ClassBuilder()._abstract().build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, clazz));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

}
