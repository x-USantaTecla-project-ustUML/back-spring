package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MethodTest {

    @Test
    void testGivenMethodWhenMatchesThenTrue() {
        for (String method : new String[]{
                "Type name()",
                "Type name(Type name)",
                "public Type name(Type name)",
                "public Type name(Type name, Type name2)",
                "public static Type name(Type name, Type name2)",
                "public abstract Type name(Type name, Type name2)"}) {
            assertTrue(Method.matches(method), "error: " + method);
        }
    }

    @Test
    void testGivenMethodWhenMatchesThenFalse() {
        this.method = "private static abstract Type name(Type name, Type name)";
        assertFalse(Method.matches(method));
        this.method = "private static abstract Type name( )";
        assertFalse(Method.matches(method));
    }

}
