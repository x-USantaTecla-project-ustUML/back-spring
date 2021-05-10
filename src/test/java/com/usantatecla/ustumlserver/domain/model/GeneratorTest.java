package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class GeneratorTest {

    protected Generator generator;
    protected Class clazz;

    @BeforeAll
    void beforeAll() {
        this.clazz = new ClassBuilder().publik().abstrat()
                .attribute().privat()
                .attribute().estatic()
                .method()
                .method().publik().parameter()
                .method().privat().abstrat().parameter().parameter()
                .build();
    }

}
