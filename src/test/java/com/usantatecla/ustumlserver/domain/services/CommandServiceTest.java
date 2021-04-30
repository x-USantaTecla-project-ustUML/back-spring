package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Package;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CommandServiceTest {

    private JSONObject json;
    private String addSimple;
    private String addComposite;

    @BeforeEach
    void beforeEach() {
        addSimple = "{\n" +
                        "  \"add\": {\n" +
                        "    \"members\": [\n" +
                        "      {\n" +
                        "        \"class\": \"Name\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}";
        addComposite = "{\n" +
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
    }

    @Test
    void testGivenCommandServiceWhenParseSimpleThenReturn() throws JSONException {
        this.json = new JSONObject(this.addSimple);
        CommandService commandService = new CommandService();
        Member member = new Package("name", Collections.singletonList(new ClassBuilder().build()));
        assertThat(commandService.parse(this.json), is(member));
    }

    @Test
    void testGivenCommandServiceWhenParseCompositeThenReturn() throws JSONException {
        this.json = new JSONObject(this.addComposite);
        CommandService commandService = new CommandService();
        Member member = new Package("name", Arrays.asList(new ClassBuilder().build(), new ClassBuilder().build()));
        assertThat(commandService.parse(this.json), is(member));
    }

}
