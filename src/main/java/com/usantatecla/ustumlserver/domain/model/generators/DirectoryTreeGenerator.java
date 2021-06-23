package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;

import java.util.List;
import java.util.StringJoiner;

public class DirectoryTreeGenerator extends Generator {

    @Override
    public String visit(Account account) {
        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.merge(new StringJoiner("").add("{")
                .add("\"id\": ")
                .add("\"" + account.getId() + "\", ")
                .add("\"name\": ")
                .add("\"" + account.getEmail() + "\""));
        if (account.getProjects().size() != 0) {
            stringJoiner.add(", \"children\": [");
            for (Member member : account.getProjects()) {
                stringJoiner.add(member.accept(this));
                if (!member.equals(account.getProjects().get(account.getProjects().size() - 1))) {
                    stringJoiner.add(", ");
                }
            }
            stringJoiner.add("]");
        }
        stringJoiner.add("}");
        return stringJoiner.toString();
    }

    @Override
    public String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.merge(new StringJoiner("").add("{")
                .add("\"id\": ")
                .add("\"" + pakage.getId() + "\", ")
                .add("\"name\": ")
                .add("\"" + pakage.getName() + "\""));
        List<Package> packageMembers = pakage.getPackageMembers();
        if (packageMembers.size() != 0) {
            stringJoiner.add(", \"children\": [");
            for (Package packageMember : packageMembers) {
                stringJoiner.add(packageMember.accept(this));
                if (!packageMember.equals(packageMembers.get(packageMembers.size() - 1))) {
                    stringJoiner.add(", ");
                }
            }
            stringJoiner.add("]");
        }
        stringJoiner.add("}");
        return stringJoiner.toString();
    }

    @Override
    public String visit(Class clazz) {
        return "";
    }

    @Override
    public String visit(Enum _enum) {
        return "";
    }

    @Override
    public String visit(Actor actor) {
        return "";
    }

}
