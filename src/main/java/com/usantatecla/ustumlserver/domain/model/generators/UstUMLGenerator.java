package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.classDiagram.*;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;

import java.util.StringJoiner;

public class UstUMLGenerator extends UMLGenerator {

    private static final String MEMBERS = "members:";
    private static final String MEMBER = "member:";
    private static final String RELATIONS = "relations:";
    private static final String OBJECTS = "objects:";
    private static final String OBJECT = "object:";

    @Override
    public String visit(Account account) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        if (++this.depthLevel == UMLGenerator.MAX_DEPTH && !account.getProjects().isEmpty()) {
            stringJoiner.add(UstUMLGenerator.MEMBERS);
            for (Member member : account.getProjects()) {
                stringJoiner.add(UMLGenerator.TAB_CHAR + "- " + this.tabulate(member.accept(this)));
            }
        }
        return stringJoiner.toString();
    }

    @Override
    public String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(pakage.getUstName()).add(pakage.getName()));
        if (++this.depthLevel == UMLGenerator.MAX_DEPTH && !pakage.getMembers().isEmpty()) {
            stringJoiner.add(UstUMLGenerator.MEMBERS);
            for (Member member : pakage.getMembers()) {
                stringJoiner.add(UMLGenerator.TAB_CHAR + "- " + this.tabulate(member.accept(this)));
            }
        }
        stringJoiner.merge(this.drawRelations(pakage));
        return stringJoiner.toString();
    }

    @Override
    public String visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(clazz.getUstName()).add(clazz.getName()));
        stringJoiner.merge(new StringJoiner(" ").add("modifiers:").add(this.getModifiersUML(clazz.getModifiers())));
        if (!(clazz.getAttributes().isEmpty() && clazz.getMethods().isEmpty())) {
            stringJoiner.add(this.getClassMembers(clazz));
        }
        stringJoiner.merge(this.drawRelations(clazz)).toString();
        return stringJoiner.toString();
    }

    private String getClassMembers(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.add(UstUMLGenerator.MEMBERS);
        for (Attribute attribute : clazz.getAttributes()) {
            stringJoiner.merge(new StringJoiner(" ").add(UMLGenerator.TAB_CHAR + "-").add(UstUMLGenerator.MEMBER).add(this.getUML(attribute)));
        }
        for (Method method : clazz.getMethods()) {
            stringJoiner.merge(new StringJoiner(" ").add(UMLGenerator.TAB_CHAR + "-").add(UstUMLGenerator.MEMBER).add(this.getUML(method)));
        }
        return stringJoiner.toString();
    }

    @Override
    public String visit(Enum _enum) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(_enum.getUstName()).add(_enum.getName()));
        stringJoiner.merge(new StringJoiner(" ").add("modifiers:").add(this.getModifiersUML(_enum.getModifiers())));
        if (!(_enum.getObjects().isEmpty())) {
            stringJoiner.add(UstUMLGenerator.OBJECTS);
            for (String object : _enum.getObjects()) {
                stringJoiner.merge(new StringJoiner(" ").add(UMLGenerator.TAB_CHAR + "-").add(UstUMLGenerator.OBJECT).add(object));
            }
        }
        if (!(_enum.getAttributes().isEmpty() && _enum.getMethods().isEmpty())) {
            stringJoiner.add(this.getClassMembers(_enum));
        }
        stringJoiner.merge(this.drawRelations(_enum)).toString();
        return stringJoiner.toString();
    }

    @Override
    public String visit(Actor actor) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(actor.getUstName()).add(actor.getName()));
        stringJoiner.merge(this.drawRelations(actor));
        return stringJoiner.toString();
    }

    @Override
    public String visit(UseCase useCase) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(useCase.getUstName()).add(useCase.getName()));
        stringJoiner.merge(this.drawRelations(useCase));
        return stringJoiner.toString();
    }

    private StringJoiner drawRelations(Member member) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        if (!member.getRelations().isEmpty()) {
            stringJoiner.add(UstUMLGenerator.RELATIONS);
            for (Relation relation : member.getRelations()) {
                stringJoiner.add(UMLGenerator.TAB_CHAR + "- " + this.tabulate(this.getUML(relation)));
            }
        }
        return stringJoiner;
    }

    @Override
    String getUML(Relation relation) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.add(relation.getUstName() + " " + relation.getTarget().getName());
        if (!relation.getRole().equals("")) {
            stringJoiner.add("role: " + relation.getRole());
        }
        return stringJoiner.toString();
    }

    @Override
    String getUML(Modifier modifier) {
        return modifier.getUstUML();
    }

    @Override
    String getUML(String name, String type) {
        return new StringJoiner(" ").add(type).add(name).toString();
    }

    @Override
    String getUML(Method method) {
        return new StringJoiner(" ")
                .add(this.getModifiersUML(method.getModifiers()))
                .add(method.getType()).add(method.getName()) +
                this.getParametersUML(method.getParameters());
    }

}
