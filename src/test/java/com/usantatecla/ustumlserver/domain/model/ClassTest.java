package com.usantatecla.ustumlserver.domain.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassTest {

    @Test
    void testGivenClassWhenMatchModifiersThenTrue() {
        for (String modifiers : new String[]{
                "public    abstract",
                "package",
                "abstract"}) {
            assertTrue(Class.matchesModifiers(modifiers), "error: " + modifiers);
        }
    }

    @Test
    void testGivenClassWhenMatchModifiersThenFalse() {
        for (String modifier : new String[]{
                "public    private",
                "public    abstra",
                "packageabstract",
                " abstract ",
                " ",
                ""}) {
            assertFalse(Class.matchesModifiers(modifier), "error: " + modifier);
        }
    }

    @Test
    void testGivenClassWhenMatchNameThenTrue() {
        for (String name : new String[]{
                "name",
                "Name",
                "name9_$",
                "nAMe"}) {
            assertTrue(Class.matchesName(name), "error: " + name);
        }
    }

    @Test
    void testGivenClassWhenMatchNameThenFalse() {
        for (String name : new String[]{
                "9",
                "#name",
                " ",
                ""}) {
            assertFalse(Class.matchesName(name), "error: " + name);
        }
    }

}
