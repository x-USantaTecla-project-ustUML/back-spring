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
            AttributeParser attributeParser = new AttributeParser();
            this.attributes = attributeParser.get(command);
            MethodParser methodParser = new MethodParser();
            this.methods = methodParser.get(command);
        }
        Class clazz = this.createClass();
        clazz.setMethods(this.methods);
        return clazz;
    }

    protected Class createClass() {
        return new Class(this.name, this.modifiers, this.attributes);
    }

    @Override
    public MemberParser copy() {
        return new ClassParser();
    }

}
