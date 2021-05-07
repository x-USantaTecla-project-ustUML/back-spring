package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttributeTest {

    private String attribute;

    @Test
    void testGivenAttributeWhenMatchesThenTrue() {
        for (String attribute : new String[]{
                "protected  static Type name",
                "public Type   name",
                "static final   Type name",
                "Type   name"}) {
            assertTrue(Attribute.matches(attribute), "error: " + attribute);
        }
    }

    @Test
    void testGivenAttributeWhenMatchesThenFalse() {
        this.attribute = "protected statc Type name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "protected static name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "public static Type";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "public private name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "protected static Type name name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "abstract Type name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "staticfinal Type name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "final final Type name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "name";
        assertFalse(Attribute.matches(attribute));
        this.attribute = "";
        assertFalse(Attribute.matches(attribute));
    }

}
