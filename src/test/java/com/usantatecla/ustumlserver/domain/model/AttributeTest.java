package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AttributeTest {

    private String attribute;

    @Test
    void testGivenAttributeWhenMatchesThenTrue() {
        this.attribute = "protected  static Type name";
        assertTrue(Attribute.matches(attribute));
        this.attribute = "public Type   name";
        assertTrue(Attribute.matches(attribute));
        this.attribute = "static final   Type name";
        assertTrue(Attribute.matches(attribute));
        this.attribute = "Type   name";
        assertTrue(Attribute.matches(attribute));
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
