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
        this.modifiers = "public    private";
        assertFalse(Class.matchesModifiers(modifiers));
        this.modifiers = "public    abstra";
        assertFalse(Class.matchesModifiers(modifiers));
        this.modifiers = "packageabstract";
        assertFalse(Class.matchesModifiers(modifiers));
        this.modifiers = " abstract ";
        assertFalse(Class.matchesModifiers(modifiers));
        this.modifiers = " ";
        assertFalse(Class.matchesModifiers(modifiers));
        this.modifiers = "";
        assertFalse(Class.matchesModifiers(modifiers));
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
        this.name = "9";
        assertFalse(Class.matchesName(name));
        this.name = " ";
        assertFalse(Class.matchesName(name));
        this.name = "";
        assertFalse(Class.matchesName(name));
    }

}
