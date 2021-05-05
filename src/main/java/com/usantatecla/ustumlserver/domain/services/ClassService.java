package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ClassService implements MemberService {

    static final String MODIFIERS_KEY = "modifiers";
    static final String MEMBERS_KEY = "members";
    static final String MEMBER_KEY = "member";

    private String name;
    private List<Modifier> modifiers;
    private List<Attribute> attributes;
    private List<Method> methods;

    ClassService() {
        this.modifiers = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    @Override
    public Member add(Command command) {
        this.name = command.getMemberName();
        if (command.has(ClassService.MODIFIERS_KEY)) {
            this.parseModifiers(command);
        }
        if (command.has(ClassService.MEMBERS_KEY)) {
            this.parseMembers(command);
        }
        Class clazz = new Class(this.name, this.modifiers, this.attributes);
        clazz.setMethods(this.methods);
        return clazz;
    }

    private void parseModifiers(Command command) {
        String modifiers = command.getString(ClassService.MODIFIERS_KEY);
        if (this.matchesClassModifiers(modifiers)) {
            for (String modifier : modifiers.split(" ")) {
                this.modifiers.add(Modifier.get(modifier));
            }
        } else  {
            throw new CommandParserException("");
        }
    }

    private void parseMembers(Command command) {
        for (Command member : command.getCommands(ClassService.MEMBERS_KEY)) {
            this.parseMember(member);
        }
    }

    private void parseMember(Command member) {
        String memberString = member.getString(ClassService.MEMBER_KEY);
        //TODO regex memberString
        if (!memberString.contains("(")) {
            this.attributes.add((Attribute) this.getDefinition(memberString));
        } else {
            String[] splitMethod = memberString.split("\\(");
            Method method = (Method) this.getDefinition(splitMethod[0]);
            method.setParameters(this.getMethodParameters(splitMethod[1]));
            this.methods.add(method);
        } else throw new CommandParserException("errrrrror");
    }

    private boolean matchesAttribute(String attribute) {
        //TODO validar que el tipo comience con mayúscula
        return attribute.matches("((public|package|private|protected)( static)?( final)?( [a-zA-Z]+)( [a-zA-Z]+))");
    }

    private boolean matchesMethod(String method) {
        return method.matches("((public|package|private)( static|abstract)?( [a-zA-Z]+){2}\\((([a-zA-Z]+){2}(, (([a-zA-Z]+){2})+)?)?\\))");
    }

    private Definition getDefinition(String definition) {
        List<String> attribute = Arrays.asList(definition.split(" "));
        List<Modifier> modifiers = this.getDefinitionModifiers(attribute);
        String type = attribute.get(modifiers.size());
        String name = attribute.get(modifiers.size() + 1);
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
        for (String parameter : parametersString.split(", ")) {
            String[] splitParameter = parameter.split(" ");
            parameters.add(new Parameter(splitParameter[1], splitParameter[0]));
        }
        return parameters;
    }

}
