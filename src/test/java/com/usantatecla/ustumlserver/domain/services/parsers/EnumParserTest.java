package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.builders.EnumBuilder;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnumParserTest {

    @Test
    void testGivenEnumParserWhenGetSimpleClassThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   enum: " + name +
                "       ,objects:[" +
                "           {object: \"PAKO\"}," +
                "           {object: \"KARMA\"}," +
                "           {object: \"JASMINE\"}" +
                "       ]" +
                "}").build();
        Enum expected = new EnumBuilder().name(name).objects("PAKO", "KARMA", "JASMINE").build();
        assertThat(new EnumParser().get(command), is(expected));
    }

    @Test
    void testGivenEnumParserWhenGetSimpleEnumThenError() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   enum: " + name +
                "       ,objects:[" +
                "           {object: \"PAKO(new PAKO())\"}," +
                "           {object: \"KARMA\"}," +
                "           {object: \"JASMINE\"}" +
                "       ]" +
                "}").build();
        assertThrows(ParserException.class, () -> new EnumParser().get(command), "error: " + name);
    }

}
