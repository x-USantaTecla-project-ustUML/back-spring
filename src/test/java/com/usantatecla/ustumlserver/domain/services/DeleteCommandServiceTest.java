package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.PackageBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ProjectBuilder;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.TestSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

    @Autowired
    private TestSeeder testSeeder;

    @BeforeEach
    void beforeEach() {
        this.testSeeder.initialize();
    }

    @Test
    void testGivenCommandServiceWhenAccountExecuteDeleteProjectThenReturn() {
        this.testSeeder.seedRelations();
        String name = "project";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               project: " + name +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Account expected = new AccountBuilder(TestSeeder.ACCOUNT)
                .build();
        expected.setProjects(new ArrayList<>());
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT)
                .build()));
        assertThat(this.commandService.execute(command, TestSeeder.SESSION_ID), is(expected));
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
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT)
                .build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, TestSeeder.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceWhenProjectExecuteDeleteMemberThenReturn() {
        String packageName = "pakageName";
        String className = "interface";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               package: " + packageName +
                "           }," +
                "           {" +
                "               interface:" + className +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Project expected = new ProjectBuilder(TestSeeder.PROJECT)
                .build();
        expected.setMembers(new ArrayList<>());
        when(this.sessionService.read(anyString())).thenReturn(List.of(new ProjectBuilder(TestSeeder.PROJECT)
                .build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
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
        String className = "class";
        String enumName = "enum";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               class: " + className +
                "           }," +
                "           {" +
                "               enum:" + enumName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder(TestSeeder.PACKAGE)
                .build();
        expected.setMembers(new ArrayList<>());
        when(this.sessionService.read(anyString())).thenReturn(List.of(new PackageBuilder(TestSeeder.PACKAGE)
                .build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
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

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteMemberWithRelationThenReturn() {
        this.testSeeder.seedRelations();
        String enumName = "enum";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               enum: " + enumName +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder(TestSeeder.PACKAGE)
                .build();
        Class _class = new ClassBuilder(TestSeeder.CLASS)
                .build();
        _class.setRelations(new ArrayList<>(List.of(TestSeeder.AGGREGATION)));
        expected.setMembers(new ArrayList<>(List.of(_class)));
        when(this.sessionService.read(anyString())).thenReturn(List.of(new PackageBuilder(TestSeeder.PACKAGE)
                .build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

    @Test
    void testGivenCommandServiceWhenPackageExecuteDeleteRelationThenReturn() {
        this.testSeeder.seedRelations();
        String targetRoute = "project.interface";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       relations: [" +
                "           {" +
                "               inheritance: " + targetRoute +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder(TestSeeder.PACKAGE)
                .build();
        expected.setRelations(new ArrayList<>(List.of(TestSeeder.ASSOCIATION)));
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(), new PackageBuilder(TestSeeder.PACKAGE)
                .build()));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(expected));
    }

}
