package com.usantatecla.ustumlserver.domain.model;

import java.util.List;
import java.util.StringJoiner;

public class DirectoryTreeGenerator {

    public String generate(Member member) {
        return member.accept(this);
    }

    String visit(Account account) {
        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.merge(new StringJoiner("").add("{")
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

    String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.merge(new StringJoiner("").add("{")
                .add("\"name\": ")
                .add("\"" + pakage.getName() + "\""));
        List<Package> packageMembers = pakage.getPackageMembers();
        if (packageMembers.size() != 0) {
            stringJoiner.add(", \"children\": [");
            for (Package packageMember : packageMembers) {
                stringJoiner.add(packageMember.accept(this));
                if (!(packageMember == packageMembers.get(packageMembers.size() - 1))) {
                    stringJoiner.add(", ");
                }
            }
            stringJoiner.add("]");
        }
        stringJoiner.add("}");
        return stringJoiner.toString();
    }

    String visit(Class clazz) {
        return "";
    }
}
