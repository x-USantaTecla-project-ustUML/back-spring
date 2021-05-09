package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UstUMLGeneratorTest extends GeneratorTest {

    @BeforeEach
    void beforeEach() {
        this.generator = new UstUMLGenerator();
    }

    @Test
    void testGivenGeneratorWhenVisitClassThenReturn() {
        String uml = "class: Name\n" +
                "modifiers: public abstract\n" +
                "members:\n" +
                "\t- member: private Type name\n" +
                "\t- member: package static Type name\n" +
                "\t- member: package Type name()\n" +
                "\t- member: public Type name(Type name)\n" +
                "\t- member: private abstract Type name(Type name, Type name)";
        assertThat(this.generator.visit(this.clazz), is(uml));
    }

    @Test
    void testGivenGeneratorWhenVisitPackageThenReturn() {
        String uml = "package: name\n" +
                "members:\n" +
                "\t- class: Name\n" +
                "\tmodifiers: public abstract\n" +
                "\tmembers:\n" +
                "\t\t- member: private Type name\n" +
                "\t\t- member: package static Type name\n" +
                "\t\t- member: package Type name()\n" +
                "\t\t- member: public Type name(Type name)\n" +
                "\t\t- member: private abstract Type name(Type name, Type name)";
        assertThat(this.generator.visit(new Package("name", Collections.singletonList(this.clazz))), is(uml));
    }

}
