package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttributeTest {

    @Test
    public void testGivenAttributeWhenGetUSTUMLThenError() {
        assertThrows(AssertionError.class, ()-> new AttributeBuilder()
                .build().getUSTUML());
        assertThrows(AssertionError.class, ()-> new AttributeBuilder()
                .name("attribute")
                .build().getUSTUML());
        assertThrows(AssertionError.class, ()-> new AttributeBuilder()
                .name("attribute")
                .type("int")
                .build().getUSTUML());
    }

    @Test
    public void testGivenAttributeWhenGetUSTUMLThenReturn() {
        assertThat(new AttributeBuilder()
                .name("attribute")
                .type("int")
                .modifiers(Modifier.PRIVATE)
                .build().getUSTUML(), is("private int attribute"));
        assertThat(new AttributeBuilder()
                .name("attr")
                .type("string")
                .modifiers(Modifier.PUBLIC)
                .build().getUSTUML(), is("public string attr"));
        assertThat(new AttributeBuilder()
                .name("attr")
                .type("Game")
                .modifiers(Modifier.PUBLIC)
                .build().getUSTUML(), is("public Game attr"));
    }

    @Test
    public void testGivenAttributeWhenGetPlantUMLThenError() {
        assertThrows(AssertionError.class, ()-> new AttributeBuilder()
                .build().getPlantUML());
        assertThrows(AssertionError.class, ()-> new AttributeBuilder()
                .name("attribute")
                .build().getPlantUML());
        assertThrows(AssertionError.class, ()-> new AttributeBuilder()
                .name("attribute")
                .type("int")
                .build().getPlantUML());
    }

    @Test
    public void testGivenAttributeWhenGetPlantUMLThenReturn() {
        assertThat(new AttributeBuilder()
                .name("attribute")
                .type("int")
                .modifiers(Modifier.PRIVATE)
                .build().getPlantUML(), is("- int attribute"));
        assertThat(new AttributeBuilder()
                .name("attr")
                .type("string")
                .modifiers(Modifier.PUBLIC)
                .build().getPlantUML(), is("+ string attr"));
        assertThat(new AttributeBuilder()
                .name("attr")
                .type("Game")
                .modifiers(Modifier.PUBLIC)
                .build().getPlantUML(), is("+ Game attr"));
    }

}
