package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.ClassBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassServiceTest {

    @Test
    void testGivenClassServiceWhenAddSimpleClassThenReturn() {
        String name = "a";
        Command command = new CommandBuilder().command("{" +
                "   class: " + name +
                "}").build();
        Class expected = new ClassBuilder().name(name).build();
        assertThat(new ClassService().add(command), is(expected));
    }

    @Test
    void testGivenClassServiceWhenAddModifiersClassThenReturn() {
        String input = "{" +
                "   class: Name," +
                "   modifiers: \"public abstract\"" +
                "}";
        Class expected = new ClassBuilder()._public()._abstract().build();
        Command command = new CommandBuilder().command(input).build();
        assertThat(new ClassService().add(command), is(expected));
    }

    @Test
    void testGivenClassServiceWhenAddCompleteClassThenReturn() {
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
        assertThat(new ClassService().add(command), is(expected));
    }

    @Test
    void testGivenClassServiceWhenAddClassThenThrowBadClassKey() {
        String input = "{" +
                "   ust: Name" +
                "}";
        Command command = new CommandBuilder().command(input).build();
        assertThrows(CommandParserException.class, () -> new ClassService().add(command));
    }

    @Test
    void testGivenClassServiceWhenAddClassThenThrowBadClassNameValue() {
        for (String name : new String[]{
                "9",
                "#name",
                " ",
                ""}) {
            String input = "{" +
                    "   class: \"" + name + "\"" +
                    "}";
            Command command = new CommandBuilder().command(input).build();
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + name);
        }
    }

    @Test
    void testGivenClassServiceWhenAddNameClassThenReturn() {
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
            assertThat(new ClassService().add(command), is(expected));
        }
    }

    @Test
    void testGivenClassServiceWhenAddModifiersThenThrowBadModifiersValue() {
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
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + modifier);
        }
    }

    @Test
    void testGivenClassServiceWhenAddModifiersThenReturn() {
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
            assertThat(new ClassService().add(command), is(entry.getValue()));
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddAttributeThenBadAttributeValue() {
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
            assertThrows(CommandParserException.class, () -> new ClassService().add(command));
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddAttributeThenReturn() {
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
            assertThat(new ClassService().add(command), is(entry.getValue()));
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddMethodThenBadMethodValue() {
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
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + method);
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddMethodThenReturn() {
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
            assertThat(new ClassService().add(command), is(entry.getValue()));
        }
    }

}
