package com.usantatecla.ustumlserver.domain.services.parsers.classDiagram;

import com.usantatecla.ustumlserver.domain.model.classDiagram.*;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassMemberParser {

    public static final String MEMBER_KEY = "member";

    private List<Attribute> attributes;
    private List<Method> methods;

    public ClassMemberParser() {
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    public void parse(Command command) {
        for (Command member : command.getCommands(ClassParser.MEMBERS_KEY)) {
            this.parseMember(member.getString(ClassMemberParser.MEMBER_KEY));
        }
    }

    public void parseModify(Command command) {
        for (Command member : command.getCommands(ClassParser.MEMBERS_KEY)) {
            this.parseMember(member.getString(Command.SET));
        }
    }

    private void parseMember(String memberString) {
        if (!memberString.contains("(") && Attribute.matches(memberString)) {
            Attribute attribute = this.getAttribute(memberString);
            this.attributes.add(attribute);
        } else if (Method.matches(memberString)) {
            this.methods.add(this.getMethod(memberString));
        } else throw new ParserException(ErrorMessage.INVALID_CLASS_MEMBER, memberString);
    }

    private Attribute getAttribute(String attributeString) {
        Definition definition = this.getDefinition(attributeString);
        return new Attribute(definition.getName(), definition.getType(), definition.getModifiers());
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

    private Method getMethod(String methodString) {
        String[] splitMethod = methodString.split("\\(");
        Definition definition = this.getDefinition(splitMethod[0]);
        Method method = new Method(definition.getName(), definition.getType(), definition.getModifiers());
        method.setParameters(this.getMethodParameters(splitMethod[1]));
        return method;
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

    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    public List<Method> getMethods() {
        return this.methods;
    }

}
