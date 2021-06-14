package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttributeParser {

    List<Attribute> attributes;

    public AttributeParser() {
        this.attributes = new ArrayList<>();
    }

    public List<Attribute> get(Command command) {
        for (Command member : command.getCommands(ClassParser.MEMBERS_KEY)) {
            this.parseMember(member);
        }
        return this.attributes;
    }

    private void parseMember(Command member) {
        String memberString = member.getString(ClassParser.MEMBER_KEY);
        if (!memberString.contains("(") && Attribute.matches(memberString)) {
            Attribute attribute = this.getAttribute(memberString);
            this.attributes.add(attribute);
        }
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


}
