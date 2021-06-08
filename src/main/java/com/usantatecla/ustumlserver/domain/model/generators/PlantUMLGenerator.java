package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;

import java.util.StringJoiner;

public class PlantUMLGenerator extends Generator {

    @Override
    public String visit(Account account) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
        if (++this.depthLevel == Generator.MAX_DEPTH) {
            for (Member member : account.getProjects()) {
                stringJoiner.add(this.tabulate(member.accept(this)));
                for (Relation relation : member.getRelations()) {
                    stringJoiner.add(this.tabulate(relation.accept(this, member)));
                }
            }
        }
        return stringJoiner.toString();
    }

    @Override
    public String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
        stringJoiner.merge(new StringJoiner(" ").add(pakage.getPlantUml()).add(this.getName(pakage)).add("{"));
        if (++this.depthLevel == Generator.MAX_DEPTH) {
            for (Member member : pakage.getMembers()) {
                stringJoiner.add(Generator.TAB_CHAR + this.tabulate(member.accept(this)));
                for (Relation relation : member.getRelations()) {
                    stringJoiner.add(this.tabulate(relation.accept(this, member)));
                }
            }
        }
        return stringJoiner.add("}").toString();
    }

    @Override
    public String visit(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
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
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
        for (Attribute attribute : clazz.getAttributes()) {
            stringJoiner.add(Generator.TAB_CHAR + this.getUML(attribute));
        }
        for (Method method : clazz.getMethods()) {
            stringJoiner.add(Generator.TAB_CHAR + this.getUML(method));
        }
        return stringJoiner.add("}").toString();
    }

    @Override
    public String visit(Enum _enum) {
        StringJoiner stringJoiner = new StringJoiner(Generator.EOL_CHAR);
        stringJoiner.add(this.getClassHeader(_enum));
        for (String object: _enum.getObjects()) {
            stringJoiner.add(object);
        }
        return stringJoiner.add(this.getClassMembers(_enum)).toString();
    }

    private String getName(Member member) {
        return "\"" + member.getName() + "\" as " + member.getId();
    }

    @Override
    public String visit(Relation relation, Member origin) {
        String plantUMLUse = "\"" + origin.getId() + "\" " + relation.getPlantUml() + " \"" + relation.getTargetId() + "\"";
        if (!relation.getRole().equals("")) {
            plantUMLUse += " : " + relation.getRole();
        }
        return plantUMLUse;
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
