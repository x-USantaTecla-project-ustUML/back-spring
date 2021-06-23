package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Attribute;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Method;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Modifier;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ClassParser extends MemberParser {

    public static final String MODIFIERS_KEY = "modifiers";
    public static final String MEMBERS_KEY = "members";

    protected List<Modifier> modifiers;
    protected List<Attribute> attributes;
    private List<Method> methods;

    public ClassParser(Account account) {
        super(account);
        this.modifiers = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        this.parseName(command.getMemberName());
        if (command.has(ClassParser.MODIFIERS_KEY)) {
            this.modifiers = new ModifierParser().get(command.getString(ClassParser.MODIFIERS_KEY));
        }
        if (command.has(ClassParser.MEMBERS_KEY)) {
            ClassMemberParser classMemberParser = new ClassMemberParser();
            classMemberParser.parse(command);
            this.attributes = classMemberParser.getAttributes();
            this.methods = classMemberParser.getMethods();
        }
        Class clazz = this.createClass();
        clazz.setMethods(this.methods);
        this.addRelations(command, clazz);
        return clazz;
    }

    protected Class createClass() {
        return new Class(this.name, this.modifiers, this.attributes);
    }

    @Override
    public MemberParser copy(Account account) {
        return new ClassParser(account);
    }

}
