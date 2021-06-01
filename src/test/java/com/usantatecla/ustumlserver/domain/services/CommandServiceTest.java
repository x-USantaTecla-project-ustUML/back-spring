package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfig
class CommandServiceTest {

    private static String SESSION_ID = "TEST";
    private static String TOKEN = "token";

    @Mock
    private SessionService sessionService;

    @Autowired
    @InjectMocks
    private CommandService commandService;
    @Autowired
    private TestSeeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    @Test
    void testGivenCommandServiceWhenExecuteAddProjectThenReturn() {
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
        when(this.sessionService.read(anyString(), anyString())).thenReturn(List.of(Seeder.ACCOUNT));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID, CommandServiceTest.TOKEN), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteAddNotProjectThenThrowException() {
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
        when(this.sessionService.read(anyString(), anyString())).thenReturn(List.of(Seeder.ACCOUNT));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID, CommandServiceTest.TOKEN));
    }

    @Test
    void testGivenCommandServiceWhenExecuteThenReturn() {
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
        Package expected = new PackageBuilder().id(Seeder.PROJECT_ID).clazz().name(name).build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new PackageBuilder().id(Seeder.PROJECT_ID).build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID, CommandServiceTest.TOKEN), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteThenReturnEmptyPackage() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder().id(Seeder.PROJECT_ID).build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new PackageBuilder().id(Seeder.PROJECT_ID).build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID, CommandServiceTest.TOKEN), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteAddCommandWithExistingClassThenReturn() {
        String firstName = "First";
        String secondName = "Second";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {class: " + firstName + "}," +
                "           {class: " + secondName + "}" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder().id(Seeder.PROJECT_ID).clazz().name(firstName).clazz().name(secondName).build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new PackageBuilder().id(Seeder.PROJECT_ID).build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID, CommandServiceTest.TOKEN), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteSameNameThenThrowException() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               package: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new PackageBuilder().id(Seeder.PROJECT_ID).clazz().name(name).build()));
        assertThrows(ParserException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID, CommandServiceTest.TOKEN));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenThenReturn() {
        String name = "name";
        Command command = new CommandBuilder().command("{" +
                "   open:" + name +
                "}").build();
        Class expected = new ClassBuilder().name(name).build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new PackageBuilder().id(Seeder.PROJECT_ID).classes(expected).build()));
        assertThat(this.commandService.execute(command, TestSeeder.SESSION_ID, CommandServiceTest.TOKEN), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   open: mariano" +
                "}").build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new PackageBuilder().id(Seeder.PROJECT_ID).build()));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID, CommandServiceTest.TOKEN));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenOnClassThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   open: mariano" +
                "}").build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new ClassBuilder().build()));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID, CommandServiceTest.TOKEN));
    }

    @Test
    void testGivenCommandServiceWhenExecuteCloseThenReturn() {
        Command command = new CommandBuilder().command("{" +
                "   close: null" +
                "}").build();
        Class clazz = new ClassBuilder().build();
        Package expected = new PackageBuilder().classes(clazz).build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Arrays.asList(expected, clazz));
        assertThat(this.commandService.execute(command, TestSeeder.SESSION_ID, CommandServiceTest.TOKEN), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteCloseThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   close: null" +
                "}").build();
        when(this.sessionService.read(anyString(), anyString())).thenReturn(Collections.singletonList(new ClassBuilder().build()));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID, CommandServiceTest.TOKEN));
    }

}
