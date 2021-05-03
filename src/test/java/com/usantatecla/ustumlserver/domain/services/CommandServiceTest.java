package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Package;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CommandServiceTest {

    @Test
    void testGivenCommandServiceWhenParseSimpleThenReturn() throws JSONException {
        String command = "{\n" +
                "  \"add\": {\n" +
                "    \"members\": [\n" +
                "      {\n" +
                "        \"class\": \"Name\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        JSONObject json = new JSONObject(command);
        CommandService commandService = new CommandService();
        Member member = new Package("name", Collections.singletonList(new ClassBuilder().build()));
        assertThat(commandService.get(json), is(member));
    }

    @Test
    void testGivenCommandServiceWhenParseCompositeThenReturn() throws JSONException {
        String command = "{\n" +
                "  \"add\": {\n" +
                "    \"members\": [\n" +
                "      {\n" +
                "        \"class\": \"Name\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"class\": \"Name\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        JSONObject json = new JSONObject(command);
        CommandService commandService = new CommandService();
        Member member = new Package("name", Arrays.asList(new ClassBuilder().build(), new ClassBuilder().build()));
        assertThat(commandService.get(json), is(member));
    }

    @Test
    void testGivenCommandServiceWhenParseCompositeThenCommandNotFound() throws JSONException {
        String command = "{\n" +
                "  \"dafads\": {\n" +
                "    \"members\": [\n" +
                "      {\n" +
                "        \"class\": \"Name\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        JSONObject json = new JSONObject(command);
        CommandService commandService = new CommandService();
        assertThrows(CommandParserException.class, () -> commandService.get(json));
    }

}
