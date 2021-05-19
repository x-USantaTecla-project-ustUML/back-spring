package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.TestConfig;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestConfig
public class CommandServiceTest {

   /* @Autowired
    private CommandService commandService;
    @Autowired
    private Seeder seeder;

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
        assertThat(this.commandService.execute(command), is(expected));
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
        assertThat(this.commandService.execute(command), is(expected));
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
        assertThat(this.commandService.execute(command), is(expected));
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
        assertThat(this.commandService.execute(command), is(expected));
    }*/

}
