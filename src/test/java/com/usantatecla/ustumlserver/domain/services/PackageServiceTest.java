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
        Command command = new CommandBuilder().add().classes(new ClassBuilder().build()).build();
        Package pakage = new Package("name", Collections.singletonList(new ClassBuilder().build()));
        assertThat(this.packageService.get(command), is(pakage));
    }

    @Test
    void testGivenPackageServiceWhenGetThenReturnEmptyPackage() {
        Command command = new CommandBuilder().add().classes().build();
        Package pakage = new Package("name", new ArrayList<>());
        assertThat(this.packageService.get(command), is(pakage));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsCommandNotFound() {
        Command command = new CommandBuilder().badKey().build();
        assertThrows(CommandParserException.class, ()->this.packageService.get(command));
    }

    @Test
    void testGivenPackageServiceWhenGetThenThrowsMemberNotFound() {
        Command command = new CommandBuilder().add().badKey(CommandType.ADD.getName()).build();
        assertThrows(CommandParserException.class, ()->this.packageService.get(command));
    }

}
