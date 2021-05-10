package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class GeneratorTest {

    protected Generator generator;
    protected Class clazz;

    @BeforeAll
    void beforeAll() {
        Attribute[] attributes = {
                this.createAttribute(new Modifier[]{Modifier.PRIVATE}),
                this.createAttribute(new Modifier[]{Modifier.PACKAGE, Modifier.STATIC})
        };
        Parameter parameter = new Parameter("name", "Type");
        Method[] methods = {
                this.createMethod(new Modifier[]{Modifier.PACKAGE}, new Parameter[]{}),
                this.createMethod(new Modifier[]{Modifier.PUBLIC}, new Parameter[]{parameter}),
                this.createMethod(new Modifier[]{Modifier.PRIVATE, Modifier.ABSTRACT}, new Parameter[]{parameter, parameter})
        };
        this.clazz = new ClassBuilder()
                .modifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .attributes(attributes)
                .methods(methods)
                .build();
    }

}
