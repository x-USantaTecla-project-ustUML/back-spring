package com.usantatecla.ustumlserver.domain.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassTest {

    private String modifiers;
    private String name;

    @Test
    void testGivenClassWhenMatchModifiersThenTrue() {
        this.modifiers = "public    abstract";
        assertTrue(Class.matchesModifiers(modifiers));
        this.modifiers = "package";
        assertTrue(Class.matchesModifiers(modifiers));
        this.modifiers = "abstract";
        assertTrue(Class.matchesModifiers(modifiers));
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
        this.name = "name";
        assertTrue(Class.matchesName(name));
        this.name = "Name";
        assertTrue(Class.matchesName(name));
        this.name = "name9_$";
        assertTrue(Class.matchesName(name));
        this.name = "nAMe";
        assertTrue(Class.matchesName(name));
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
