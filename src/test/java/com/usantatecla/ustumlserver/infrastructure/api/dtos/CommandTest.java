package com.usantatecla.ustumlserver.infrastructure.api.dtos;

import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

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
    void testGivenCommandWhenGetMembersThenReturn() {
        Command command = new CommandBuilder().add().classes(new ClassBuilder().build()).build();
        assertThat(command.getMembers().size(), is(1));
    }

    @Test
    void testGivenCommandWhenGetMembersThenReturnEmptyList() {
        Command command = new CommandBuilder().add().build();
        assertThrows(ParserException.class, command::getMembers);
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
