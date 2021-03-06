package com.usantatecla.ustumlserver.domain.services.parsers.classDiagram;

import com.usantatecla.ustumlserver.domain.model.builders.AccountBuilder;
import com.usantatecla.ustumlserver.domain.model.builders.ClassBuilder;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.domain.services.parsers.classDiagram.ClassParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassParserTest {

    private ClassParser classParser;

    @BeforeEach
    void beforeEach() {
        this.classParser = new ClassParser(new AccountBuilder().build());
    }

    @Test
    void testGivenClassParserWhenGetSimpleClassThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   class: " + name +
                "}").build();
        Class expected = new ClassBuilder().name(name).build();
        assertThat(this.classParser.get(command), is(expected));
    }

    @Test
    void testGivenClassParserWhenGetModifiersClassThenReturn() {
        String input = "{" +
                "   class: Name," +
                "   modifiers: \"public abstract\"" +
                "}";
        Class expected = new ClassBuilder()._public()._abstract().build();
        Command command = new CommandBuilder().command(input).build();
        assertThat(this.classParser.get(command), is(expected));
    }

    @Test
    void testGivenClassParserWhenGetCompleteClassThenReturn() {
        String type = "int";
        String name = "a";
        String input = "{" +
                "   class: Name," +
                "   modifiers: \"public abstract\"," +
                "   members: [" +
                "       {" +
                "           member: \"public " + type + " " + name + "\"" +
                "       }," +
                "       {" +
                "           member: \"public " + type + " " + name + "(Type name, Type name)\"" +
                "       }" +
                "   ]" +
                "}";
        Class expected = new ClassBuilder()._public()._abstract()
                .attribute()._public().type(type).name(name)
                .method()._public().type(type).name(name).parameter().parameter()
                .build();
        Command command = new CommandBuilder().command(input).build();
        assertThat(this.classParser.get(command), is(expected));
    }

    @Test
    void testGivenClassParserWhenGetClassThenThrowBadClassKey() {
        String input = "{" +
                "   ust: Name" +
                "}";
        Command command = new CommandBuilder().command(input).build();
        assertThrows(ParserException.class, () -> this.classParser.get(command));
    }

    @Test
    void testGivenClassParserWhenGetClassThenThrowBadClassNameValue() {
        for (String name : new String[]{
                "null",
                "9",
                "#name",
                " ",
                ""}) {
            String input = "{" +
                    "   class: \"" + name + "\"" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThrows(ParserException.class, () -> this.classParser.get(command), "error: " + name);
        }
    }

    @Test
    void testGivenClassParserWhenGetNameClassThenReturn() {
        for (String name : new String[]{
                "name",
                "n",
                "Name",
                "name9_$",
                "nAMe"}) {
            String input = "{" +
                    "   class: \"" + name + "\"" +
                    "}";
            Class expected = new ClassBuilder().name(name).build();
            Command command = new CommandBuilder().command(input).build();
            assertThat(this.classParser.get(command), is(expected));
        }
    }

    @Test
    void testGivenClassParserWhenGetModifiersThenThrowBadModifiersValue() {
        for (String modifier : new String[]{
                "public    private",
                "public    abstra",
                "packageabstract",
                " abstract ",
                " ",
                ""}) {
            String input = "{" +
                    "   class: Name," +
                    "   modifiers: \"" + modifier + "\"" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThrows(ParserException.class, () -> this.classParser.get(command), "error: " + modifier);
        }
    }

    @Test
    void testGivenClassParserWhenGetModifiersThenReturn() {
        Map<String, Class> map = new HashMap<>() {{
            put("public    abstract", new ClassBuilder()._public()._abstract().build());
            put("package", new ClassBuilder().build());
            put("abstract", new ClassBuilder()._abstract().build());
        }};
        for (Map.Entry<String, Class> entry : map.entrySet()) {
            String input = "{" +
                    "   class: Name," +
                    "   modifiers: \"" + entry.getKey() + "\"" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThat(this.classParser.get(command), is(entry.getValue()));
        }
    }

    @Test
    void testGivenClassParserWhenGetAttributeThenBadAttributeValue() {
        for (String attribute : new String[]{
                "protected statc Type name",
                "protected static name",
                "public static Type",
                "public private name",
                "protected static Type name name",
                "abstract Type name",
                "staticfinal Type name",
                "final final Type name",
                "name",
                ""}) {
            String input = "{" +
                    "   class: Name," +
                    "   members: [" +
                    "       {" +
                    "           member: \"" + attribute + "\"" +
                    "       }" +
                    "   ]" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThrows(ParserException.class, () -> this.classParser.get(command));
        }
    }

    @Test
    void testGivenClassParserWhenGetAttributeThenReturn() {
        Map<String, Class> map = new HashMap<>() {{
            put("protected  static Type name", new ClassBuilder().attribute()._protected()._static().build());
            put("public Type   name", new ClassBuilder().attribute()._public().build());
            put("static final   Type name", new ClassBuilder().attribute()._static()._final().build());
            put("Type   name", new ClassBuilder().attribute().build());
        }};
        for (Map.Entry<String, Class> entry : map.entrySet()) {
            String input = "{" +
                    "   class: Name," +
                    "   members: [" +
                    "       {" +
                    "           member: \"" + entry.getKey() + "\"" +
                    "       }" +
                    "   ]" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThat(this.classParser.get(command), is(entry.getValue()));
        }
    }

    @Test
    void testGivenClassParserWhenGetMethodThenBadMethodValue() {
        for (String method : new String[]{
                "Typename()",
                "Type name( )",
                "Type name(Type)",
                "plic Type name(Type name)",
                "protected Type name(Type name,)",
                "private static abstract Type name()",
                "final static Type name()",
                "Type name ()",
                ""}) {
            String input = "{" +
                    "   class: Name," +
                    "   members: [" +
                    "       {" +
                    "           member: \"" + method + "\"" +
                    "       }" +
                    "   ]" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThrows(ParserException.class, () -> this.classParser.get(command), "error: " + method);
        }
    }

    @Test
    void testGivenClassParserWhenGetMethodThenReturn() {
        Map<String, Class> map = new HashMap<>() {{
            put("Type    name()", new ClassBuilder().method().build());
            put("Type name(Type   name)", new ClassBuilder().method().parameter().build());
            put("public   Type name(Type   name)", new ClassBuilder().method()._public().parameter().build());
            put("public   abstract Type   name(Type name, Type   name)",
                    new ClassBuilder().method()._public()._abstract().parameter().parameter().build());
        }};
        for (Map.Entry<String, Class> entry : map.entrySet()) {
            String input = "{" +
                    "   class: Name," +
                    "   members: [" +
                    "       {" +
                    "           member: \"" + entry.getKey() + "\"" +
                    "       }" +
                    "   ]" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThat(this.classParser.get(command), is(entry.getValue()));
        }
    }

}
