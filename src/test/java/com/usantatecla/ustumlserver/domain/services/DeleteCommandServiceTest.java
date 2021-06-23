package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.ModelException;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.builders.*;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.services.interpreters.InterpretersStack;
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

    @Autowired
    private TestSeeder testSeeder;

    @Mock
    private SessionService sessionService;

    @Autowired
    @InjectMocks
    private InterpretersStack interpretersStack;

    private CommandService commandService;

    @BeforeEach
    void beforeEach() {
        this.testSeeder.initialize();
        this.commandService = new CommandService(this.interpretersStack);
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

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteNotExistentMemberThenThrowException() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               member: \"private int notExist\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteAttributeThenIsDeleted() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               member: \"private int attribute\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        TestSeeder.ABSTRACT_CLASS.setAttributes(new ArrayList<>());
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(TestSeeder.ABSTRACT_CLASS));
    }

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteMethodThenReturn() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               member: \"private String method(int param1, String param2)\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        TestSeeder.ABSTRACT_CLASS.setMethods(new ArrayList<>());
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(TestSeeder.ABSTRACT_CLASS));
    }

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteNotExistentRelationThenThrowException() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       relations: [" +
                "           {" +
                "               use: \"notExist.test\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteRelationThenReturn() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       relations: [" +
                "           {" +
                "               use: \"project.interface\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        TestSeeder.ABSTRACT_CLASS.setRelations(new ArrayList<>());
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(TestSeeder.ABSTRACT_CLASS));
    }

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteNotExistentMemberAndRelationsThenThrowException() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               member: \"private String notExist\"" +
                "           }" +
                "       ], " +
                "       relations: [" +
                "           {" +
                "               use: \"project.interface\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteMembersAndNotExistentRelationsThenThrowException() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               member: \"private int attribute\"" +
                "           }" +
                "       ], " +
                "       relations: [" +
                "           {" +
                "               inheritance: \"project.notExist\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        assertThrows(ServiceException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

    @Test
    void testGivenCommandServiceInClassContextWhenDeleteMembersAndRelationsThenReturn() {
        this.testSeeder.seedAbstractClassWithRelation();
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       members: [" +
                "           {" +
                "               member: \"private int attribute\"" +
                "           }, " +
                "           {" +
                "               member: \"private String method(int param1, String param2)\"" +
                "           }" +
                "       ], " +
                "       relations: [" +
                "           {" +
                "               use: \"project.interface\"" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(),
                new ProjectBuilder(TestSeeder.PROJECT).build(),
                new ClassBuilder(TestSeeder.ABSTRACT_CLASS)
                        .build()));
        TestSeeder.ABSTRACT_CLASS.setAttributes(new ArrayList<>());
        TestSeeder.ABSTRACT_CLASS.setMethods(new ArrayList<>());
        TestSeeder.ABSTRACT_CLASS.setRelations(new ArrayList<>());
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(TestSeeder.ABSTRACT_CLASS));
    }

    @Test
    void testGivenCommandServiceEnumWhenExecuteDeleteObjectThenReturn() {
        String object = "OBJECT";
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       objects: [" +
                "           {" +
                "               object: " + object +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Enum _enum = new EnumBuilder().objects(object).build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(), _enum));
        assertThat(this.commandService.execute(command, CommandServiceTest.SESSION_ID), is(new EnumBuilder().build()));
    }

    @Test
    void testGivenCommandServiceEnumWhenExecuteDeleteNotExistentObjectThenThrowException() {
        Command command = new CommandBuilder().command("{" +
                "   delete: {" +
                "       objects: [" +
                "           {" +
                "               object: OBJECT" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        when(this.sessionService.read(anyString())).thenReturn(List.of(new AccountBuilder(TestSeeder.ACCOUNT).build(), new EnumBuilder().build()));
        assertThrows(ModelException.class, () -> this.commandService.execute(command, CommandServiceTest.SESSION_ID));
    }

}
