package com.usantatecla.ustumlserver.domain.services.parsers.classDiagram;

import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.EnumBuilder;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.EnumParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnumParserTest {

    private EnumParser enumParser;

    @BeforeEach
    void beforeEach() {
        this.enumParser = new EnumParser(new AccountBuilder().build());
    }

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
        assertThat(this.enumParser.get(command), is(expected));
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
        Assertions.assertThrows(ParserException.class, () -> this.enumParser.get(command), "error: " + name);
    }

}
