package com.usantatecla.ustumlserver.domain.services.delete;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.services.CommandService;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.SessionService;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfig
public class CommandServiceDeleteTest {

    private static String SESSION_ID = "TEST";

    @Mock
    private SessionService sessionService;
    @Autowired
    @InjectMocks
    private CommandService commandService;

    @Autowired
    private TestSeeder testSeeder;

    @BeforeEach
    void beforeEach() {
        this.testSeeder.initialize();
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteProjectThenReturn() {
        String projectName = "project";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               project: " + projectName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString()))
                .thenReturn(List.of(TestSeeder.ACCOUNT));
        Account accountResult = (Account)this.commandService.execute(command, TestSeeder.SESSION_ID);
        assertTrue(accountResult.getProjects().isEmpty());
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteMembersThenReturn() {
        String packageName = "pakageName";
        String className = "interface";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
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
        when(this.sessionService.read(anyString()))
                .thenReturn(List.of(TestSeeder.PROJECT));
        Project projectResult = (Project)this.commandService.execute(command, TestSeeder.SESSION_ID);
        assertTrue(projectResult.getMembers().isEmpty());
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteProjectThenThrowException() {
        String projectName = "pro";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               project: " + projectName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString()))
                .thenReturn(List.of(TestSeeder.ACCOUNT));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteMembersThenThrowException() {
        String packageName = "pak";
        String className = "interface";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
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
        when(this.sessionService.read(anyString()))
                .thenReturn(List.of(TestSeeder.PROJECT));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID));
    }

}
