package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import lombok.SneakyThrows;
import org.json.JSONObject;
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
        command = new CommandBuilder().clazz(new ClassBuilder().build()).build();
        assertThat(command.getCommandType(), is(CommandType.NULL));
    }

    @SneakyThrows
    @Test
    void testGivenCommandWhenGetMemberTypeThenReturn() {
        Command command = new CommandBuilder().clazz(new ClassBuilder().build()).build();
        assertThat(command.getMemberType(), is(MemberType.CLASS));
        command = new CommandBuilder().badKey().build();
        assertThat(command.getMemberType(), is(MemberType.NULL));
    }

    @Test
    void testGivenCommandWhenGetMembersThenReturn() {
        Command command = new CommandBuilder().add().classes(new ClassBuilder().build()).build();
        assertThat(command.getMembers().size(), is(1));
    }

    @Test
    void testGivenCommandWhenGetMembersThenReturnEmptyList() {
        Command command = new CommandBuilder().add().build();
        assertThrows(CommandParserException.class, command::getMembers);
    }

    @SneakyThrows
    @Test
    void testGivenCommandWhenGetMemberNameThenReturn() {
        Command command = new CommandBuilder().clazz(new ClassBuilder().build()).build();
        assertThat(command.getMemberName(), is("Name"));
        command = new CommandBuilder().badKey().build();
        assertThrows(CommandParserException.class, command::getMemberName);
    }

}
