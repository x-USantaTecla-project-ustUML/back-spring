package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfig
public class CommandServiceTest {

    private static String SESSION_ID = "TEST";

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
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID, "token"), is(expected));
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
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID, "token"), is(expected));
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
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID, "token"), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteWithPackageThenReturn() {
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
        assertThrows(ParserException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID, "token"));
    }

    /*@Test
    void testGivenCommandServiceWhenExecuteOpenThenReturn() {
        this.seeder.seedOpen();
        Command command = new CommandBuilder().command("{" +
                "   open:" + TestSeeder.PACKAGE_NAME +
                "}").build();
        assertThat(this.commandService.execute(command, TestSeeder.SESSION_ID).getId(), is(TestSeeder.PACKAGE.getId()));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenThenThrowException() {
        this.seeder.seedOpen();
        Command command = new CommandBuilder().command("{" +
                "   open: not_found" +
                "}").build();
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenOnClassThenThrowException() {
        this.seeder.seedOpen();
        Command command = new CommandBuilder().command("{" +
                "   open: " + TestSeeder.CLASS_NAME +
                "}").build();
        this.commandService.execute(command, TestSeeder.SESSION_ID);
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenExecuteCloseThenReturn() {
        this.seeder.seedClose();
        Command command = new CommandBuilder().command("{" +
                "   close: null" +
                "}").build();
        String expectedName = "name";
        assertThat(this.commandService.execute(command, TestSeeder.SESSION_ID).getName(), is(expectedName));
    }

    @Test
    void testGivenCommandServiceWhenExecuteCloseThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   close: null" +
                "}").build();
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID));
    }*/

}
