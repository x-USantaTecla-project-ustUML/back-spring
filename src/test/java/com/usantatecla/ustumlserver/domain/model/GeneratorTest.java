package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class GeneratorTest {

    protected Generator generator;
    protected Class clazz;

    @BeforeAll
    void beforeAll() {
        this.clazz = new ClassBuilder()._public()._abstract()
                .attribute()._private()
                .attribute()._static()
                .method()
                .method()._public().parameter()
                .method()._private()._abstract().parameter().parameter()
                .build();
    }

}
