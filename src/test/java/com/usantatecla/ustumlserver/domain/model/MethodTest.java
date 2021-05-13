package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodTest {

    @Test
    void testGivenMethodWhenMatchesThenTrue() {
        for (String method : new String[]{
                "Type name()",
                "Type name(Type name)",
                "public Type name(Type name)",
                "public Type name(Type name, Type name2)",
                "public static Type name(Type name, Type name2)",
                "private abstract Type name(Type name, Type name2)"}) {
            assertTrue(Method.matches(method), "error: " + method);
        }
    }

    @Test
    void testGivenMethodWhenMatchesThenFalse() {
        for (String method : new String[]{
                "Typename()",
                "Type name( )",
                "Type name(Type)",
                "plic Type name(Type name)",
                "protected Type name(Type name,)",
                "private static abstract Type name()",
                "final static Type name()",
                "Type name",
                "Type name ()",
                ""}) {
            assertFalse(Method.matches(method), "error: " + method);
        }
    }

}
