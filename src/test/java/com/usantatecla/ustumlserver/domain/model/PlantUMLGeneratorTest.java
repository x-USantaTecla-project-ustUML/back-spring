package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PlantUMLGeneratorTest extends GeneratorTest {

    @BeforeEach
    void beforeEach() {
        this.generator = new PlantUMLGenerator();
    }

    @Test
    void testGivenGeneratorWhenVisitClassThenReturn() {
        String uml = "abstract class Name {\n" +
                "\t- name: Type\n" +
                "\t~ {static} name: Type\n" +
                "\t~ name(): Type\n" +
                "\t+ name(name: Type): Type\n" +
                "\t- {abstract} name(name: Type, name: Type): Type\n" +
                "}";
        assertThat(this.generator.visit(this.clazz), is(uml));
    }

    @Test
    void testGivenGeneratorWhenVisitPackageThenReturn() {
        String uml = "package name {\n" +
                "\tabstract class Name {\n" +
                "\t\t- name: Type\n" +
                "\t\t~ {static} name: Type\n" +
                "\t\t~ name(): Type\n" +
                "\t\t+ name(name: Type): Type\n" +
                "\t\t- {abstract} name(name: Type, name: Type): Type\n" +
                "\t}\n" +
                "}";
        assertThat(this.generator.visit(new Package("name", Collections.singletonList(this.clazz))), is(uml));
    }

}
