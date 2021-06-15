package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassMemberParser {

    List<Attribute> attributes;
    List<Method> methods;

    public ClassMemberParser() {
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    public void get(Command command) {
        for (Command member : command.getCommands(ClassParser.MEMBERS_KEY)) {
            this.parseMember(member);
        }
    }

    public void modify(Command command){
        for (Command member : command.getCommands(ClassParser.MEMBERS_KEY)) {
            if (!member.has(ClassParser.SET_KEY)) {
                throw new ParserException(ErrorMessage.KEY_NOT_FOUND, ClassParser.SET_KEY);
            }
            this.parseModifyMember(member.getString(ClassParser.MEMBER_KEY), member.getString(MemberParser.SET_KEY));
        }
    }

    private void parseMember(Command member) {
        String memberString = member.getString(ClassParser.MEMBER_KEY);
        if (!memberString.contains("(") && Attribute.matches(memberString)) {
            Attribute attribute = this.getAttribute(memberString);
            this.attributes.add(attribute);
        }else if (Method.matches(memberString)) {
            this.methods.add(this.getMethod(memberString));
        }else throw new ParserException(ErrorMessage.INVALID_CLASS_MEMBER, memberString);
    }

    private void parseModifyMember(String oldName, String newName) {
        if (!oldName.contains("(") && Attribute.matches(oldName) &&
                !newName.contains("(") && Attribute.matches(newName)) {
            this.attributes.add(this.getAttribute(oldName));
            this.attributes.add(this.getAttribute(newName));
        }else if (Method.matches(oldName) && Method.matches(newName)) {
            this.methods.add(this.getMethod(oldName));
            this.methods.add(this.getMethod(newName));
        }else throw new ParserException(ErrorMessage.INVALID_CLASS_MEMBER, newName);
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

    public List<Attribute> getAttributes(){
        return this.attributes;
    }

    public List<Method> getMethods(){
        return this.methods;
    }

}
