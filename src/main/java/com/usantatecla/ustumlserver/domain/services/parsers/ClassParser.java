package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.services.Command;
import com.usantatecla.ustumlserver.domain.services.Error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassParser extends MemberParser {

    static final String MODIFIERS_KEY = "modifiers";
    static final String MEMBERS_KEY = "members";
    static final String MEMBER_KEY = "member";

    private List<Modifier> modifiers;
    private List<Attribute> attributes;
    private List<Method> methods;

    ClassParser() {
        this.modifiers = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        this.parseName(command);
        if (command.has(ClassParser.MODIFIERS_KEY)) {
            this.parseModifiers(command);
        }
        if (command.has(ClassParser.MEMBERS_KEY)) {
            this.parseMembers(command);
        }
        Class clazz = new Class(this.name, this.modifiers, this.attributes);
        clazz.setMethods(this.methods);
        return clazz;
    }

    private void parseModifiers(Command command) {
        String modifiers = command.getString(ClassParser.MODIFIERS_KEY);
        if (Class.matchesModifiers(modifiers)) {
            List<String> splitModifiers = new ArrayList<>(Arrays.asList(modifiers.split(" ")));
            splitModifiers.removeIf(""::equals);
            for (String modifier : splitModifiers) {
                this.modifiers.add(Modifier.get(modifier));
            }
        } else {
            throw new CommandParserException(Error.INVALID_CLASS_MODIFIERS, modifiers);
        }
    }

    private void parseMembers(Command command) {
        for (Command member : command.getCommands(ClassParser.MEMBERS_KEY)) {
            this.parseMember(member);
        }
    }

    private void parseMember(Command member) {
        String memberString = member.getString(ClassParser.MEMBER_KEY);
        if (!memberString.contains("(") && Attribute.matches(memberString)) {
            this.attributes.add(this.getAttribute(memberString));
        } else if (Method.matches(memberString)) {
            this.methods.add(this.getMethod(memberString));
        } else throw new CommandParserException(Error.INVALID_CLASS_MEMBER, memberString);
    }

    private Attribute getAttribute(String attributeString) {
        Definition definition = this.getDefinition(attributeString);
        return new Attribute(definition.getName(), definition.getType(), definition.getModifiers());
    }

    private Method getMethod(String methodString) {
        String[] splitMethod = methodString.split("\\(");
        Definition definition = this.getDefinition(splitMethod[0]);
        Method method = new Method(definition.getName(), definition.getType(), definition.getModifiers());
        method.setParameters(this.getMethodParameters(splitMethod[1]));
        return method;
    }

    private Definition getDefinition(String definitionString) {
        List<String> definitions = new ArrayList<>(Arrays.asList(definitionString.split(" ")));
        definitions.removeIf(""::equals);
        List<Modifier> modifiers = this.getDefinitionModifiers(definitions);
        String type = definitions.get(modifiers.size());
        String name = definitions.get(modifiers.size() + 1);
        return new Definition(name, type, modifiers);
    }

    private List<Modifier> getDefinitionModifiers(List<String> definition) {
        List<Modifier> modifiers = new ArrayList<>();
        for (String string : definition) {
            Modifier modifier = Modifier.get(string);
            if (!modifier.isNull()) {
                modifiers.add(Modifier.get(string));
            }
        }
        return modifiers;
    }

    private List<Parameter> getMethodParameters(String parametersString) {
        List<Parameter> parameters = new ArrayList<>();
        parametersString = parametersString.replace(")", "");
        if (parametersString.length() > 0) {
            if (parametersString.contains(", ")) {
                for (String parameter : parametersString.split(",")) {
                    parameters.add(this.getParameter(parameter));
                }
            } else {
                parameters.add(this.getParameter(parametersString));
            }
        }
        return parameters;
    }

    private Parameter getParameter(String parameterString) {
        List<String> splitParameter = new ArrayList<>(Arrays.asList(parameterString.split(" ")));
        splitParameter.removeIf(""::equals);
        return new Parameter(splitParameter.get(1), splitParameter.get(0));
    }

    @Override
    public MemberParser copy() {
        return new ClassParser();
    }

}