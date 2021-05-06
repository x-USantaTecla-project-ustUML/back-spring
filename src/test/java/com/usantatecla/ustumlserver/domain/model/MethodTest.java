package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MethodTest {

    private String method;

    @Test
    void testGivenMethodWhenMatchesThenTrue() {
        this.method = "Type name()";
        assertTrue(Method.matches(method));
    }

    @Test
    void testGivenMethodWhenMatchesThenFalse() {
        this.method = "private static abstract Type name(Type name, Type name)";
        assertFalse(Method.matches(method));
        this.method = "private static abstract Type name( )";
        assertFalse(Method.matches(method));
    }

}
