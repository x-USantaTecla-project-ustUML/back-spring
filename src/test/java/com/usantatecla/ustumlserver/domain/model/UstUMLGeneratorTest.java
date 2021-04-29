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
                "modifiers:\n" +
                "\t- public\n" +
                "\t- abstract\n" +
                "members:\n" +
                "\t- definition: private Type name\n" +
                "\t- definition: package static Type name\n" +
                "\t- definition: package Type name()\n" +
                "\t- definition: public Type name(Type name)\n" +
                "\t- definition: private abstract Type name(Type name, Type name)";
        assertThat(this.generator.toString(), is(uml));
    }

}
