package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassServiceTest {

    @Test
    void testGivenClassServiceWhenAddSimpleClassThenReturn() {
        Class clazz = new ClassBuilder().build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThat(new ClassService().add(command), is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddModifiersClassThenReturn() {
        Class clazz = new ClassBuilder().modifiers(Modifier.PUBLIC, Modifier.ABSTRACT).build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThat(new ClassService().add(command), is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddCompleteClassThenReturn() {
        Class clazz = new ClassBuilder().modifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .attributes(new AttributeBuilder().modifiers(Modifier.PUBLIC).build())
                .methods(new MethodBuilder().modifiers(Modifier.PUBLIC).parameters(new Parameter("name", "Type"), new Parameter("name", "Type")).build())
                .build();
        Command command = new CommandBuilder().clazz(clazz).build();
        Class aClass = new ClassService().add(command);
        assertThat(aClass, is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddClassThenThrowBadClassKey() {
        Command command = new CommandBuilder().badKey().build();
        assertThrows(CommandParserException.class, () -> new ClassService().add(command));
    }

    @Test
    void testGivenClassServiceWhenAddClassThenThrowBadClassNameValue() {
        for (String name : new String[]{
                "9",
                "#name",
                " ",
                ""}) {
            Command command = new CommandBuilder().clazz(new ClassBuilder().name(name).build()).build();
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + name);
        }
    }

    @Test
    void testGivenClassServiceWhenAddNameClassThenReturn() {
        for (String name : new String[]{
                "name",
                "Name",
                "name9_$",
                "nAMe"}) {
            Class clazz = new ClassBuilder().name(name).build();
            Command command = new CommandBuilder().clazz(clazz).build();
            assertThat(new ClassService().add(command), is(clazz));
        }
    }

    @Test
    void testGivenClassServiceWhenAddModifiersThenThrowBadModifiersValue() {
        Class clazz = new ClassBuilder().modifiers().build();
        for (String modifier : new String[]{
                "public    private",
                "public    abstra",
                "packageabstract",
                " abstract ",
                " ",
                ""}) {
            Command command = new CommandBuilder().clazz(clazz).value("modifiers", modifier).build();
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + modifier);
        }
    }

    @Test
    void testGivenClassServiceWhenAddModifiersThenReturn() {
        for (String modifier : new String[]{
                "public    abstract",
                "package",
                "abstract"}) {
            Class clazz = new ClassBuilder().modifiers(modifier).build();
            Command command = new CommandBuilder().clazz(clazz).build();
            assertThat(new ClassService().add(command), is(clazz));
        }
    }

}
