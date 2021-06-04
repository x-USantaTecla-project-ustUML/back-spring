package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;

import java.util.StringJoiner;

public class UstUMLGenerator extends Generator {

    private static final String MEMBERS = "members:";
    private static final String MEMBER = "member:";
    private static final String RELATIONS = "relations:";

    @Override
    public String visit(Account account) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
        if (++this.depthLevel == Generator.MAX_DEPTH && !account.getProjects().isEmpty()) {
            stringJoiner.add(UstUMLGenerator.MEMBERS);
            for (Member member : account.getProjects()) {
                stringJoiner.add(Generator.TAB_CHAR + "- " + this.tabulate(member.accept(this)));
            }
        }
        return stringJoiner.toString();
    }

    @Override
    public String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(pakage.getUstName()).add(pakage.getName()));
        if (++this.depthLevel == Generator.MAX_DEPTH) {
            if (!pakage.getMembers().isEmpty()) {
                stringJoiner.add(UstUMLGenerator.MEMBERS);
                for (Member member : pakage.getMembers()) {
                    stringJoiner.add(Generator.TAB_CHAR + "- " + this.tabulate(member.accept(this)));
                }
            }
            this.drawRelations(pakage, stringJoiner);
        }
        return stringJoiner.toString();
    }

    @Override
    public String visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(clazz.getUstName()).add(clazz.getName()));
        stringJoiner.merge(new StringJoiner(" ").add("modifiers:").add(this.getModifiersUML(clazz.getModifiers())));
        if (!(clazz.getAttributes().isEmpty() && clazz.getMethods().isEmpty())) {
            stringJoiner.add(UstUMLGenerator.MEMBERS);
            for (Attribute attribute : clazz.getAttributes()) {
                stringJoiner.merge(new StringJoiner(" ").add(Generator.TAB_CHAR + "-").add(UstUMLGenerator.MEMBER).add(this.getUML(attribute)));
            }
            for (Method method : clazz.getMethods()) {
                stringJoiner.merge(new StringJoiner(" ").add(Generator.TAB_CHAR + "-").add(UstUMLGenerator.MEMBER).add(this.getUML(method)));
            }
        }
        this.drawRelations(clazz, stringJoiner);
        return stringJoiner.toString();
    }

    private void drawRelations(Member member, StringJoiner stringJoiner) {
        if (!member.getRelations().isEmpty()) {
            stringJoiner.add(UstUMLGenerator.RELATIONS);
            for (Relation relation : member.getRelations()) {
                stringJoiner.add(Generator.TAB_CHAR + "- " + this.tabulate(relation.accept(this, member)));
            }
        }
    }

    @Override
    public String visit(Relation relation, Member origin) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
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