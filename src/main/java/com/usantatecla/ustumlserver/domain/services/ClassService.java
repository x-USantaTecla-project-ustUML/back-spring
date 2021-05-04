package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ClassService implements MemberService {

    static final String NAME = "class";
    static final String MODIFIERS = "modifiers";
    static final String MEMBERS = "members";
    static final String DEFINITION = "definition";

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
        if (json.has(ClassService.MEMBERS)) {
            this.parseMembers(json);
        }
        Class clazz = new Class(this.name, this.modifiers, this.attributes);
        clazz.setMethods(this.methods);
        return clazz;
    }

    private void parseModifiers(JSONObject json) {
        String modifiers = new JSONKeyFinder(json).getString(ClassService.MODIFIERS);
        //TODO regex orden modifiers
        for (String modifier : modifiers.split(" ")) {
            this.modifiers.add(Modifier.get(modifier));
        }
    }

    private void parseMembers(JSONObject json) {
        JSONArray members = new JSONKeyFinder(json).getJSONArray(ClassService.MEMBERS);
        for (int i = 0; i < members.length(); i++) {
            this.parseDefinition(new JSONKeyFinder(members).getJSONObject(i));
        }
    }

    private void parseDefinition(JSONObject json) {
        String definition = new JSONKeyFinder(json).getString(ClassService.DEFINITION);
        //TODO regex definition
        if (!definition.contains("(")) {
            this.attributes.add((Attribute) this.getDefinition(definition));
        } else {
            String[] splitMethod = memberString.split("\\(");
            Method method = (Method) this.getDefinition(splitMethod[0]);
            method.setParameters(this.getMethodParameters(splitMethod[1]));
            this.methods.add(method);
        }
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
