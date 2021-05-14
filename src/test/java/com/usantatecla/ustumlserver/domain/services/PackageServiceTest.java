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
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
public class PackageServiceTest {

    @Autowired
    private PackageService packageService;
    @Autowired
    private Seeder seeder;

    @BeforeEach
    void beforeEach() {
        this.seeder.initialize();
    }

    @Test
    void testGivenPackageServiceWhenGetThenReturn() {
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
        assertThat(this.packageService.get(command), is(expected));
    }

    @Test
    void testGivenPackageServiceWhenGetThenReturnEmptyPackage() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder().build();
        assertThat(this.packageService.get(command), is(expected));
    }

    @Test
    void testGivenPackageServiceWhenAddExistClassThenReturn() {
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
        assertThat(this.packageService.get(command), is(expected));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsCommandNotFound() {
        Command command = new CommandBuilder().command("{" +
                "   ust: {" +
                "   }" +
                "}").build();
        assertThrows(CommandParserException.class, () -> this.packageService.get(command));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsMembersNotFound() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "   }" +
                "}").build();
        assertThrows(CommandParserException.class, () -> this.packageService.get(command));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsMemberNotFound() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {ust: name}" +
                "       ]" +
                "   }" +
                "}").build();
        assertThrows(CommandParserException.class, () -> this.packageService.get(command));
    }

    @Test
    void testGivenPackageServiceWhenAddExistClassThenThrowsMemberAlreadyExists() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {class: Name}," +
                "           {class: Name}" +
                "       ]" +
                "   }" +
                "}").build();
        assertThrows(CommandParserException.class, () -> this.packageService.get(command));
    }

}
