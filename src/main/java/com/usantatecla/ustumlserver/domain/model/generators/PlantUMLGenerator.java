package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.WithMembersMember;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.classDiagram.*;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;

import java.util.StringJoiner;

public class PlantUMLGenerator extends UMLGenerator {

    private WithMembersMember withMembersMember;
    private Member origin;

    @Override
    public String visit(Account account) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        if (++this.depthLevel <= UMLGenerator.MAX_DEPTH) {
            for (Member member : account.getProjects()) {
                stringJoiner.add(this.tabulate(member.accept(this)));
            }
            this.withMembersMember = account;
            for (Member member : account.getProjects()) {
                this.origin = member;
                for (Relation relation : member.getRelations()) {
                    stringJoiner.add(this.getUML(relation));
                }
            }
        }
        return stringJoiner.toString();
    }

    @Override
    public String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(UMLGenerator.ALLOW_MIXING).add(pakage.getPlantUml()).add(this.getName(pakage)).add("{"));
        if (++this.depthLevel <= UMLGenerator.MAX_DEPTH) {
            for (Member member : pakage.getMembers()) {
                stringJoiner.add(UMLGenerator.TAB_CHAR + this.tabulate(member.accept(this)));
            }
            stringJoiner.add("}");
            this.withMembersMember = pakage;
            for (Member member : pakage.getMembers()) {
                this.origin = member;
                for (Relation relation : member.getRelations()) {
                    stringJoiner.add(this.getUML(relation));
                }
            }
            return stringJoiner.toString();
        } else {
            return stringJoiner.add("}").toString();
        }
    }

    @Override
    public String visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.add(this.getClassHeader(clazz));
        return stringJoiner.add(this.getClassMembers(clazz)).toString();
    }

    private String getClassHeader(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (Modifier modifier : clazz.getModifiers()) {
            if (!modifier.isVisibility()) {
                stringJoiner.add(modifier.getPlantUML());
            }
        }
        stringJoiner.add(clazz.getPlantUml()).add(this.getName(clazz)).add("{");
        return stringJoiner.toString();
    }

    private String getClassMembers(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        for (Attribute attribute : clazz.getAttributes()) {
            stringJoiner.add(UMLGenerator.TAB_CHAR + this.getUML(attribute));
        }
        for (Method method : clazz.getMethods()) {
            stringJoiner.add(UMLGenerator.TAB_CHAR + this.getUML(method));
        }
        return stringJoiner.add("}").toString();
    }

    @Override
    public String visit(Enum _enum) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        stringJoiner.add(this.getClassHeader(_enum));
        for (String object : _enum.getObjects()) {
            stringJoiner.add(object);
        }
        return stringJoiner.add(this.getClassMembers(_enum)).toString();
    }

    @Override
    public String visit(Actor actor) {
        StringJoiner stringJoiner = new StringJoiner(UMLGenerator.EOL_CHAR);
        return stringJoiner.merge(new StringJoiner(" ").add(actor.getPlantUml()).add(this.getName(actor))).toString();
    }

    private String getName(Member member) {
        return "\"" + member.getName() + "\" as " + member.getId();
    }

    @Override
    String getUML(Relation relation) {
        String plantUML = "";
        if (this.withMembersMember.find(relation.getTargetName()) == null) {
            plantUML = relation.getTargetPlantUML() + " \"" + relation.getTargetRoute() + "\" as " + relation.getTargetId() + "{}\n";
        }
        plantUML += this.origin.getId() + " " + relation.getPlantUml() + " " + relation.getTargetId();
        if (!relation.getRole().equals("")) {
            plantUML += " : " + relation.getRole();
        }
        return plantUML;
    }

    @Override
    String getUML(Modifier modifier) {
        if (modifier != Modifier.FINAL) {
            if (!modifier.isVisibility()) {
                return "{" + modifier.getPlantUML() + "}";
            }
            return modifier.getPlantUML();
        }
        return "";
    }

    @Override
    String getUML(String name, String type) {
        return new StringJoiner(" ").add(name + ":").add(type).toString();
    }

    @Override
    String getUML(Method method) {
        return new StringJoiner(" ")
                .add(this.getModifiersUML(method.getModifiers()))
                .add(method.getName()) +
                this.getParametersUML(method.getParameters()) + ": " +
                method.getType();
    }

}
