package com.usantatecla.ustumlserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MethodTest {

    @Test
    public void testGivenMethodWhenGetUSTUMLThenError() {
        assertThrows(AssertionError.class, ()-> new MethodBuilder()
                .build().getUSTUML());
        assertThrows(AssertionError.class, ()-> new MethodBuilder()
                .name("Method")
                .build().getUSTUML());
        assertThrows(AssertionError.class, ()-> new MethodBuilder()
                .name("Method")
                .type("int")
                .build().getUSTUML());
    }

    @Test
    public void testGivenMethodWhenGetUSTUMLThenReturn() {
        assertThat(new MethodBuilder()
                .name("method")
                .type("int")
                .modifiers(Modifier.PRIVATE)
                .build().getUSTUML(), is("private int method()"));
        assertThat(new MethodBuilder()
                .name("meth")
                .type("void")
                .modifiers(Modifier.PUBLIC)
                .parameters(new Parameter("param", "int"))
                .build().getUSTUML(), is("public void meth(int param)"));
        assertThat(new MethodBuilder()
                .name("attr")
                .type("Game")
                .modifiers(Modifier.PUBLIC)
                .parameters(new Parameter("param1", "int"), new Parameter("param2", "Game"))
                .build().getUSTUML(), is("public Game attr(int param1, Game param2)"));
    }

    @Test
    public void testGivenMethodWhenGetPlantUMLThenError() {
        assertThrows(AssertionError.class, ()-> new MethodBuilder()
                .build().getPlantUML());
        assertThrows(AssertionError.class, ()-> new MethodBuilder()
                .name("Method")
                .build().getPlantUML());
        assertThrows(AssertionError.class, ()-> new MethodBuilder()
                .name("Method")
                .type("int")
                .build().getPlantUML());
    }

    @Test
    public void testGivenMethodWhenGetPlantUMLThenReturn() {
        assertThat(new MethodBuilder()
                .name("method")
                .type("int")
                .modifiers(Modifier.PRIVATE)
                .build().getPlantUML(), is("- int method()"));
        assertThat(new MethodBuilder()
                .name("meth")
                .type("void")
                .modifiers(Modifier.PUBLIC)
                .parameters(new Parameter("param", "int"))
                .build().getPlantUML(), is("+ void meth(int param)"));
        assertThat(new MethodBuilder()
                .name("attr")
                .type("Game")
                .modifiers(Modifier.PUBLIC)
                .parameters(new Parameter("param1", "int"), new Parameter("param2", "Game"))
                .build().getPlantUML(), is("+ Game attr(int param1, Game param2)"));
    }

}
