package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.ModelException;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
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
class DeleteCommandServiceTest {

    @Mock
    protected SessionService sessionService;
    @Autowired
    @InjectMocks
    protected CommandService commandService;

    @Test
    void testGivenCommandServiceWhenAccountExecuteDeleteProjectThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
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
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(Seeder.ACCOUNT));
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteDeleteNotExistentProjectThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               project: a" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(Seeder.ACCOUNT));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteDeleteMemberThenReturn() {
        String packageName = "a";
        String className = "b";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               package: " + packageName +
                "           }," +
                "           {" +
                "               class:" + className +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Project project = new ProjectBuilder()
                .pakage().name(packageName)
                .clazz().name(className)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(project));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(new ProjectBuilder().build()));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteDeleteNotExistentMemberThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               interface: a" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new ProjectBuilder().build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteMemberThenReturn() {
        String packageName = "a";
        String className = "b";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               package: " + packageName +
                "           }," +
                "           {" +
                "               class:" + className +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package pakage = new PackageBuilder()
                .pakage().name(packageName)
                .clazz().name(className)
                .build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(pakage));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(new PackageBuilder().build()));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteNotExistentMemberThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               interface: a" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new PackageBuilder().build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

}
