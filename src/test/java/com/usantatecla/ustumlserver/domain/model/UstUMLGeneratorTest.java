package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UstUMLGeneratorTest extends GeneratorTest {

    @BeforeEach
    void beforeEach() {
        this.generator = new UstUMLGenerator();
    }

    @Test
    void testGivenGeneratorWhenToStringThenReturn() {
        this.clazz.accept(this.generator);
        String uml = "class: Name\n" +
                "modifiers: public abstract\n" +
                "members:\n" +
                "\t- member: private Type name\n" +
                "\t- member: package static Type name\n" +
                "\t- member: package Type name()\n" +
                "\t- member: public Type name(Type name)\n" +
                "\t- member: private abstract Type name(Type name, Type name)";
        assertThat(this.generator.toString(), is(uml));
    }

}
