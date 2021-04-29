package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PlantUMLGeneratorTest extends GeneratorTest {

    @BeforeEach
    void beforeEach() {
        this.generator = new PlantUMLGenerator();
    }

    @Test
    void testGivenGeneratorWhenToStringThenReturn() {
        this.clazz.accept(this.generator);
        String uml = "abstract class Name {\n" +
                "- name: Type\n" +
                "~ {static} name: Type\n" +
                "~ name: Type()\n" +
                "+ name: Type(name: Type)\n" +
                "- {abstract} name: Type(name: Type, name: Type)\n" +
                "}";
        assertThat(this.generator.toString(), is(uml));
    }

}
