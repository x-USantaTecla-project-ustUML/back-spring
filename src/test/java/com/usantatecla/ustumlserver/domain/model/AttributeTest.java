package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttributeTest {

    @Test
    void testGivenAttributeWhenMatchesThenTrue() {
        for (String attribute : new String[]{
                "protected  static Type name",
                "public Type   name",
                "static final   Type name",
                "Type   name",
                "private Type   name"}) {
            assertTrue(Attribute.matches(attribute), "error: " + attribute);
        }
    }

    @Test
    void testGivenAttributeWhenMatchesThenFalse() {
        for (String attribute : new String[]{
                "protected statc Type name",
                "protected static name",
                "public static Type",
                "public private name",
                "protected static Type name name",
                "abstract Type name",
                "staticfinal Type name",
                "final final Type name",
                "name",
                ""}) {
            assertFalse(Attribute.matches(attribute), "error: " + attribute);
        }
    }

}
