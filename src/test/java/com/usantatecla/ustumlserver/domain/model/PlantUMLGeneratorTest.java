package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                "  - name: Type\n" +
                "  ~ {static} name: Type\n" +
                "  ~ name(): Type\n" +
                "  + name(name: Type): Type\n" +
                "  - {abstract} name(name: Type, name: Type): Type\n" +
                "}";
        assertThat(this.generator.visit(this.clazz), is(uml));
    }

    @Test
    void testGivenGeneratorWhenVisitPackageThenReturn() {
        String uml = "package name {\n" +
                "  abstract class Name {\n" +
                "      - name: Type\n" +
                "      ~ {static} name: Type\n" +
                "      ~ name(): Type\n" +
                "      + name(name: Type): Type\n" +
                "      - {abstract} name(name: Type, name: Type): Type\n" +
                "    }\n" +
                "}";
        assertThat(this.generator.visit(new Package("name", Collections.singletonList(this.clazz))), is(uml));
    }

}