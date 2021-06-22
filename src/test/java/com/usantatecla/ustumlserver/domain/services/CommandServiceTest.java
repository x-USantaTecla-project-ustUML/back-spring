package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.domain.services.interpreters.InterpretersStack;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import org.junit.jupiter.api.BeforeEach;
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
class CommandServiceTest {

    protected static String SESSION_ID = "TEST";

    @Mock
    private SessionService sessionService;

    @Autowired
    @InjectMocks
    private InterpretersStack interpretersStack;

    private CommandService commandService;

    @BeforeEach
    void beforeEach() {
        this.commandService = new CommandService(this.interpretersStack);
    }

    @Test
    void testGivenCommandServiceWhenExecuteNotExistentCommandThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   non: {" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT));
        assertThrows(ParserException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteOpenThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   open: " + name +
                "}").build();
        Project expected = new ProjectBuilder().name(name).build();
        Account account = new AccountBuilder(Seeder.ACCOUNT)
                .projects(expected)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(account));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteOpenNotExistentThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   open: non" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteOpenThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   open: " + name +
                "}").build();
        Package expected = new PackageBuilder().name(name).build();
        Project project = new ProjectBuilder()
                .pakage(expected)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, project));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteOpenNotExistentThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   open: non" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ProjectBuilder().build()));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenMemberExecuteOpenThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   open: non" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, new ClassBuilder().build()));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteCloseThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   close: non" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteCloseThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   close: non" +
                "}").build();
        Project project = new ProjectBuilder().name(name).build();
        Account expected = new AccountBuilder(Seeder.ACCOUNT)
                .projects(project)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(expected, project));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenGetContextThenReturn() {
        Project expected = new ProjectBuilder().build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT, expected));
        assertThat(this.commandService.getContext(CommandServiceTest.SESSION_ID), is(expected));
    }



}
