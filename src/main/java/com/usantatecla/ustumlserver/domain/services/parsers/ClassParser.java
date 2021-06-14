package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassParser extends MemberParser {

    public static final String MODIFIERS_KEY = "modifiers";
    public static final String MEMBERS_KEY = "members";
    public static final String MEMBER_KEY = "member";

    protected List<Modifier> modifiers;
    protected List<Attribute> attributes;
    private List<Method> methods;

    public ClassParser() {
        this.modifiers = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        this.parseName(command.getMemberName());
        if (command.has(ClassParser.MODIFIERS_KEY)) {
            ModifierParser modifierParser = new ModifierParser();
            this.modifiers = modifierParser.get(command);
        }
        if (command.has(ClassParser.MEMBERS_KEY)) {
            this.parseMembers(command);
        }
        Class clazz = this.createClass();
        clazz.setMethods(this.methods);
        return clazz;
    }

    @Override
    public Member getModifiedMember(Command command) {
        this.parseName(command.getString(MemberParser.SET_KEY));
        /*if (command.has(ClassParser.MODIFIERS_KEY)) {
            this.parseModifiers(command);
        }
        if (command.has(ClassParser.MEMBERS_KEY)) {
            this.parseMembers(command);
        }*/
        Class clazz = this.createClass();
        clazz.setMethods(this.methods);
        return clazz;
    }

    protected Class createClass() {
        return new Class(this.name, this.modifiers, this.attributes);
    }

    public void parseMembers(Command command) {
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
        } else throw new ParserException(ErrorMessage.INVALID_CLASS_MEMBER, memberString);
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
