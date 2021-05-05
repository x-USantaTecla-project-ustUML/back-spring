package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassServiceTest {

    private ClassService classService;

    @BeforeEach
    void beforeEach() {
        this.classService = new ClassService();
    }

    @Test
    void testGivenClassServiceWhenAddSimpleClassThenReturn() {
        Class clazz = new ClassBuilder().build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThat(this.classService.add(command), is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddModifiersClassThenReturn() {
        Class clazz = new ClassBuilder().modifiers(Modifier.PUBLIC, Modifier.ABSTRACT).build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThat(this.classService.add(command), is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddCompleteClassThenReturn() {
        Class clazz = new ClassBuilder().modifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .attributes(new AttributeBuilder().modifiers(Modifier.PUBLIC).build())
                .methods(new MethodBuilder().modifiers(Modifier.PUBLIC).build())
                .build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThat(this.classService.add(command), is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddModifiersClassThenThrowModifiersError() {
        Class clazz = new ClassBuilder().modifiers(Modifier.ABSTRACT).build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThrows(CommandParserException.class, () -> this.classService.add(command));
    }

}
