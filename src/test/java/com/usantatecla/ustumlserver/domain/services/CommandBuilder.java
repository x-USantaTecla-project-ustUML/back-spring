package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.StringJoiner;

class CommandBuilder {

    private static final String BAD_KEY = "bad";

    private JSONObject jsonObject;
    private CommandType commandType;

    CommandBuilder() {
        this.jsonObject = new JSONObject();
        this.commandType = CommandType.NULL;
    }

    @SneakyThrows
    CommandBuilder command(String command) {
        this.jsonObject = new JSONObject(command);
        return this;
    }

    @SneakyThrows
    CommandBuilder add() {
        this.jsonObject.accumulate(CommandType.ADD.getName(), "");
        this.commandType = CommandType.ADD;
        return this;
    }

    @SneakyThrows
    CommandBuilder badKey() {
        this.jsonObject.accumulate(CommandBuilder.BAD_KEY, "");
        return this;
    }

    @SneakyThrows
    CommandBuilder badKey(String key) {
        assert this.jsonObject.has(key);

        this.jsonObject.put(key, new JSONObject().put(CommandBuilder.BAD_KEY, ""));
        return this;
    }

    @SneakyThrows
    CommandBuilder value(String key, Object value) {
        this.jsonObject.put(key, value);
        return this;
    }

    @SneakyThrows
    CommandBuilder classes(Class... classes) {
        assert !this.commandType.isNull();

        JSONArray jsonClasses = new JSONArray();
        for (Class clazz : classes) {
            jsonClasses.put(this.getClass(clazz));
        }
        this.jsonObject.put("add", new JSONObject().put("members", jsonClasses));
        return this;
    }

    @SneakyThrows
    CommandBuilder clazz(Class clazz) {
        assert this.commandType.isNull();

        this.jsonObject = this.getClass(clazz);
        return this;
    }

    @SneakyThrows
    private JSONObject getClass(Class clazz) {
        JSONObject jsonClass = new JSONObject();
        jsonClass.accumulate("class", clazz.getName());
        if (!clazz.getModifiers().isEmpty()) {
            jsonClass.accumulate("modifiers", this.getModifiers(clazz.getModifiers()));
        }
        if (!clazz.getAttributes().isEmpty() && !clazz.getMethods().isEmpty()) {
            jsonClass.accumulate("members", this.getClassMembers(clazz));
        }
        return jsonClass;
    }

    private String getModifiers(List<Modifier> modifiers) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Modifier modifier : modifiers) {
            stringJoiner.add(modifier.getUstUML());
        }
        return stringJoiner.toString();
    }

    @SneakyThrows
    private JSONArray getClassMembers(Class clazz) {
        JSONArray jsonMembers = new JSONArray();
        for (Attribute attribute : clazz.getAttributes()) {
            jsonMembers.put(this.getAttribute(attribute));
        }
        for (Method method : clazz.getMethods()) {
            jsonMembers.put(this.getMethod(method));
        }
        return jsonMembers;
    }

    @SneakyThrows
    private JSONObject getAttribute(Attribute attribute) {
        JSONObject jsonAttribute = new JSONObject();
        StringJoiner attributeJoiner = new StringJoiner(" ");
        attributeJoiner.add(this.getModifiers(attribute.getModifiers()))
                .add(attribute.getType())
                .add(attribute.getName());
        jsonAttribute.accumulate("member", attributeJoiner.toString());
        return jsonAttribute;
    }

    @SneakyThrows
    private JSONObject getMethod(Method method) {
        JSONObject jsonMethod = new JSONObject();
        StringJoiner methodJoiner = new StringJoiner(" ");
        methodJoiner.add(this.getModifiers(method.getModifiers()))
                .add(method.getType())
                .add(method.getName());
        jsonMethod.accumulate("member", methodJoiner.toString() +
                "(" + this.getMethodParams(method.getParameters()) + ")");
        return jsonMethod;
    }

    private String getMethodParams(List<Parameter> parameters) {
        StringJoiner paramsJoiner = new StringJoiner(", ");
        for (Parameter parameter : parameters) {
            StringJoiner paramJoiner = new StringJoiner(" ");
            paramJoiner.add(parameter.getType())
                    .add(parameter.getName());
            paramsJoiner.merge(paramJoiner);
        }
        return paramsJoiner.toString();
    }

    Command build() {
        return new Command(this.jsonObject);
    }

}
