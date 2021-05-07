package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassServiceTest {

    @Test
    void testGivenClassServiceWhenAddSimpleClassThenReturn() {
        Class clazz = new ClassBuilder().build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThat(new ClassService().add(command), is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddModifiersClassThenReturn() {
        Class clazz = new ClassBuilder().modifiers(Modifier.PUBLIC, Modifier.ABSTRACT).build();
        Command command = new CommandBuilder().clazz(clazz).build();
        assertThat(new ClassService().add(command), is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddCompleteClassThenReturn() {
        Class clazz = new ClassBuilder().modifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .attributes(new AttributeBuilder().modifiers(Modifier.PUBLIC).build())
                .methods(new MethodBuilder().modifiers(Modifier.PUBLIC).parameters(new Parameter("name", "Type"), new Parameter("name", "Type")).build())
                .build();
        Command command = new CommandBuilder().clazz(clazz).build();
        Class aClass = new ClassService().add(command);
        assertThat(aClass, is(clazz));
    }

    @Test
    void testGivenClassServiceWhenAddClassThenThrowBadClassKey() {
        Command command = new CommandBuilder().badKey().build();
        assertThrows(CommandParserException.class, () -> new ClassService().add(command));
    }

    @Test
    void testGivenClassServiceWhenAddClassThenThrowBadClassNameValue() {
        for (String name : new String[]{
                "9",
                "#name",
                " ",
                ""}) {
            Command command = new CommandBuilder().clazz(new ClassBuilder().name(name).build()).build();
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + name);
        }
    }

    @Test
    void testGivenClassServiceWhenAddNameClassThenReturn() {
        for (String name : new String[]{
                "name",
                "Name",
                "name9_$",
                "nAMe"}) {
            Class clazz = new ClassBuilder().name(name).build();
            Command command = new CommandBuilder().clazz(clazz).build();
            assertThat(new ClassService().add(command), is(clazz));
        }
    }

    @Test
    void testGivenClassServiceWhenAddModifiersThenThrowBadModifiersValue() {
        Class clazz = new ClassBuilder().modifiers().build();
        for (String modifier : new String[]{
                "public    private",
                "public    abstra",
                "packageabstract",
                " abstract ",
                " ",
                ""}) {
            Command command = new CommandBuilder().clazz(clazz).value("modifiers", modifier).build();
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + modifier);
        }
    }

    @Test
    void testGivenClassServiceWhenAddModifiersThenReturn() {
        for (String modifier : new String[]{
                "public    abstract",
                "package",
                "abstract"}) {
            Class clazz = new ClassBuilder().modifiers(modifier).build();
            Command command = new CommandBuilder().clazz(clazz).build();
            assertThat(new ClassService().add(command), is(clazz));
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddAttributeThenBadAttributeValue() {
        JSONArray jsonArray = new JSONArray();
        Class clazz = new ClassBuilder().build();
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
            JSONObject jsonObject = new JSONObject().put("member", attribute);
            Command command = new CommandBuilder().clazz(clazz).value("members", jsonArray.put(jsonObject)).build();
            assertThrows(CommandParserException.class, () -> new ClassService().add(command));
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddAttributeThenReturn() {
        Map<String, Attribute> map = new HashMap<>() {{
            put("protected  static Type name", new AttributeBuilder().modifiers(Modifier.PROTECTED, Modifier.STATIC).build());
            put("public Type   name", new AttributeBuilder().modifiers(Modifier.PUBLIC).build());
            put("static final   Type name", new AttributeBuilder().modifiers(Modifier.STATIC, Modifier.FINAL).build());
            put("Type   name", new AttributeBuilder().build());
        }};
        for (Map.Entry<String, Attribute> entry : map.entrySet()) {
            Class clazz = new ClassBuilder().attributes(entry.getValue()).build();
            JSONObject jsonObject = new JSONObject().put("member", entry.getKey());
            Command command = new CommandBuilder().clazz(new ClassBuilder().build()).value("members", new JSONArray().put(jsonObject)).build();
            assertThat(new ClassService().add(command), is(clazz));
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddMethodThenBadMethodValue() {
        Class clazz = new ClassBuilder().build();
        for (String method : new String[]{
                "Typename()",
                "Type name( )",
                "Type name(Type)",
                "plic Type name(Type name)",
                "protected Type name(Type name,)",
                "private static abstract Type name()",
                "final static Type name()",
                "Type name",
                "Type name ()",
                ""}) {
            JSONObject jsonObject = new JSONObject().put("member", new MethodBuilder().build());
            Command command = new CommandBuilder().clazz(clazz).value("members", jsonObject).build();
            assertThrows(CommandParserException.class, () -> new ClassService().add(command), "error: " + method);
        }
    }

    @SneakyThrows
    @Test
    void testGivenClassServiceWhenAddMethodThenReturn() {
        Map<String, Method> map = new HashMap<>() {{
            put("Type    name()", new MethodBuilder().build());
            put("Type name(Type   name)", new MethodBuilder().parameters(new Parameter("name", "Type")).build());
            put("public   Type name(Type   name)", new MethodBuilder().modifiers(Modifier.PUBLIC).parameters(new Parameter("name", "Type")).build());
            put("public   abstract Type   name(Type name, Type2   name2)", new MethodBuilder().modifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .parameters(new Parameter("name", "Type"), new Parameter("name2", "Type2")).build());
        }};
        for (Map.Entry<String, Method> entry : map.entrySet()) {
            Class clazz = new ClassBuilder().methods(entry.getValue()).build();
            JSONObject jsonObject = new JSONObject().put("member", entry.getKey());
            Command command = new CommandBuilder().clazz(new ClassBuilder().build()).value("members", new JSONArray().put(jsonObject)).build();
            assertThat(new ClassService().add(command), is(clazz));
        }
    }
}
