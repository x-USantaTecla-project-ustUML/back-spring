package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddServiceTest {

    @Test
    void testGivenCommandServiceWhenParseSimpleThenReturn() throws JSONException {
        String command = "{\n" +
                "  \"members\": [\n" +
                "  ]\n" +
                "}";
        JSONObject json = new JSONObject(command);
        AddService addService = new AddService();
        Member member = new Package("name", new ArrayList<>());
        assertThat(addService.get(json), is(member));
    }

    @Test
    void testGivenCommandServiceWhenParseCompositeThenReturn() throws JSONException {
        String command = "{\n" +
                "    \"members\": [\n" +
                "      {\n" +
                "        \"class\": \"Name\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"class\": \"Name\"\n" +
                "      }\n" +
                "    ]\n" +
                "}";
        JSONObject json = new JSONObject(command);
        AddService addService = new AddService();
        Member member = new Package("name", Arrays.asList(new ClassBuilder().build(), new ClassBuilder().build()));
        assertThat(addService.get(json), is(member));
    }

    @Test
    void testGivenCommandServiceWhenParseSimpleThenCommandNotFound() throws JSONException {
        String command = "{\n" +
                "    \"members\": [\n" +
                "      {\n" +
                "        \"cl\": \"Name\"\n" +
                "      }\n" +
                "    ]\n" +
                "}";
        JSONObject json = new JSONObject(command);
        AddService addService = new AddService();
        assertThrows(CommandParserException.class, () -> addService.get(json));
    }

    @Test
    void testGivenCommandServiceWhenParseCompositeThenCommandNotFound() throws JSONException {
        String command = "{\n" +
                "    \"members\": [\n" +
                "      {\n" +
                "        \"class\": \"Name\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"clazz\": \"Name\"\n" +
                "      }\n" +
                "    ]\n" +
                "}";
        JSONObject json = new JSONObject(command);
        AddService addService = new AddService();
        assertThrows(CommandParserException.class, () -> addService.get(json));
    }

}
