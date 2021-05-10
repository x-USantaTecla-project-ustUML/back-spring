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
        String input = "{" +
                "   add: {" +
                "       members: [" +
                "           {" +
                "               class: Name" +
                "           }" +
                "       ]" +
                "   }" +
                "}";
        Package expected = new PackageBuilder().clazz().build();
        Command command = new CommandBuilder().command(input).build();
        assertThat(this.packageService.get(command), is(expected));
    }

    @Test
    void testGivenPackageServiceWhenGetThenReturnEmptyPackage() {
        String input = "{" +
                "   add: {" +
                "       members: [" +
                "       ]" +
                "   }" +
                "}";
        Package expected = new PackageBuilder().build();
        Command command = new CommandBuilder().command(input).build();
        assertThat(this.packageService.get(command), is(expected));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsCommandNotFound() {
        String input = "{" +
                "   ust: {" +
                "   }" +
                "}";
        Command command = new CommandBuilder().command(input).build();
        assertThrows(CommandParserException.class, ()->this.packageService.get(command));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsMembersNotFound() {
        String input = "{" +
                "   add: {" +
                "   }" +
                "}";
        Command command = new CommandBuilder().command(input).build();
        assertThrows(CommandParserException.class, ()->this.packageService.get(command));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsMemberNotFound() {
        String input = "{" +
                "   add: {" +
                "       members: [" +
                "           {ust: name}" +
                "       ]" +
                "   }" +
                "}";
        Command command = new CommandBuilder().command(input).build();
        assertThrows(CommandParserException.class, ()->this.packageService.get(command));
    }

}
