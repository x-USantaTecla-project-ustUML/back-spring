package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.PackageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PackageServiceTest {

    private PackageService packageService;

    @BeforeEach
    void beforeEach() {
        this.packageService = new PackageService();
    }

    @Test
    void testGivenPackageServiceWhenGetThenReturn() {
        Command command = new CommandBuilder().command("{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               class: Name" +
                "           }" +
                "       ]" +
                "   }" +
                "}").build();
        Package expected = new PackageBuilder().clazz().build();
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
