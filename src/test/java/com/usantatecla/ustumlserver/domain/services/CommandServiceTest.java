package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
public class CommandServiceTest {

    private static String SESSION_ID = "TEST";

    @Autowired
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
        Package expected = new PackageBuilder().clazz().name(name).build();
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteThenReturnEmptyPackage() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder().build();
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
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
        Package expected = new PackageBuilder().clazz().name(firstName).clazz().name(secondName).build();
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
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
        Package expected = new PackageBuilder()
                .pakage(new PackageBuilder().name(name).build())
                .build();
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenThenReturn() {
        this.seeder.seedOpen();
        String name = "test";
        Command command = new CommandBuilder().command("{" +
                "   open:" + name +
                "}").build();
        Package expected = new PackageBuilder().name(name).build();
        assertThat(this.commandService.execute(command, TestSeeder.SESSION_OPEN), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenThenThrowException() {
        this.seeder.seedOpen();
        Command command = new CommandBuilder().command("{" +
                "   open: not_found" +
                "}").build();
        assertThrows(CommandParserException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_OPEN));
    }

    @Test
    void testGivenCommandServiceWhenExecuteOpenOnClassThenThrowException() {
        this.seeder.seedOpen();
        Command command = new CommandBuilder().command("{" +
                "   open: TestClass" +
                "}").build();
        this.commandService.execute(command, TestSeeder.SESSION_OPEN);
        assertThrows(CommandParserException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_OPEN));
    }

    @Test
    void testGivenCommandServiceWhenExecuteCloseThenReturn() {
        this.seeder.seedClose();
        Command command = new CommandBuilder().command("{" +
                "   close: null" +
                "}").build();
        String expectedName = "name";
        assertThat(this.commandService.execute(command, TestSeeder.SESSION_OPEN).getName(), is(expectedName));
    }

    @Test
    void testGivenCommandServiceWhenExecuteCloseThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   close: null" +
                "}").build();
        assertThrows(CommandParserException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_OPEN));
    }

}
