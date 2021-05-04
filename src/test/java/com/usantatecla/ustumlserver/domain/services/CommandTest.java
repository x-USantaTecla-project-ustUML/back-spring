package com.usantatecla.ustumlserver.domain.services;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    @SneakyThrows
    @Test
    void testGivenCommandWhenHasThenReturn() {
        Command command = new Command(new JSONObject("{key:{}}"));
        assertTrue(command.has("key"));
        assertFalse(command.has("keyy"));
    }

    @SneakyThrows
    @Test
    void testGivenCommandWhenGetCommandTypeThenReturn() {
        Command command = new Command(new JSONObject("{add:{}}"));
        assertThat(command.getCommandType(), is(CommandType.ADD));
        command = new Command(new JSONObject("{dda:{}}"));
        assertThat(command.getCommandType(), is(CommandType.NULL));
    }

    @SneakyThrows
    @Test
    void testGivenCommandWhenGetMemberTypeThenReturn() {
        Command command = new Command(new JSONObject("{class:{}}"));
        assertThat(command.getMemberType(), is(MemberType.CLASS));
        command = new Command(new JSONObject("{claz:{}}"));
        assertThat(command.getMemberType(), is(MemberType.NULL));
    }

    @SneakyThrows
    @Test
    void testGivenCommandWhenGetMembersThenReturn() {
        String member = "{class: name}";
        List<Command> members = Collections.singletonList(new Command(new JSONObject(member)));
        Command command = new Command(new JSONObject("{add:{" +
                "members:[" + member +
                "]" +
                "}}"));
        assertThat(command.getMembers().size(), is(members.size()));
    }

    @SneakyThrows
    @Test
    void testGivenCommandWhenGetMembersThenReturnEmptyList() {
        Command command = new Command(new JSONObject("{add:{}}"));
        assertThrows(CommandParserException.class, command::getMembers);
    }

    @SneakyThrows
    @Test
    void testGivenCommandWhenGetMemberNameThenReturn() {
        Command command = new Command(new JSONObject("{class:name}"));
        assertThat(command.getMemberName(), is("name"));
        command = new Command(new JSONObject("{cla:name}"));
        assertThrows(CommandParserException.class, command::getMemberName);
    }

}
