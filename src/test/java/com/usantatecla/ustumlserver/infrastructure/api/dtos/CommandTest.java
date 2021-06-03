package com.usantatecla.ustumlserver.infrastructure.api.dtos;

import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberType;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testGivenCommandWhenHasThenReturn() {
        Command command = new CommandBuilder().add().build();
        assertTrue(command.has(CommandType.ADD.getName()));
        assertFalse(command.has("addd"));
    }

    @Test
    void testGivenCommandWhenGetCommandTypeThenReturn() {
        Command command = new CommandBuilder().add().build();
        assertThat(command.getCommandType(), is(CommandType.ADD));
    }

    @Test
    void testGivenCommandWhenGetCommandTypeThenError() {
        Command command = new CommandBuilder().clazz(new ClassBuilder().build()).build();
        assertThrows(ParserException.class, command::getCommandType);
    }

    @Test
    void testGivenCommandWhenGetMemberTypeThenReturn() {
        Command command = new CommandBuilder().clazz(new ClassBuilder().build()).build();
        assertThat(command.getMemberType(), is(MemberType.CLASS));
    }

    @Test
    void testGivenCommandWhenGetMemberTypeThenError() {
        Command command = new CommandBuilder().badKey().build();
        assertThrows(ParserException.class, command::getMemberType);
    }

    @Test
    void testGivenCommandWhenGetMemberNameThenReturn() {
        Command command = new CommandBuilder().clazz(new ClassBuilder().build()).build();
        assertThat(command.getMemberName(), is("Name"));
    }

    @Test
    void testGivenCommandWhenGetMemberNameThenError() {
        Command command = new CommandBuilder().badKey().build();
        assertThrows(ParserException.class, command::getMemberName);
    }

}
