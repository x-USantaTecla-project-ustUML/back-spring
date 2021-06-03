package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;

import java.util.List;
import java.util.StringJoiner;

public class DirectoryTreeGenerator {

    public String generate(Member member) {
        return member.accept(this);
    }

    public String visit(Account account) {
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

    public String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner("");
        stringJoiner.merge(new StringJoiner("").add("{")
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

    public String visit(Class clazz) {
        return "";
    }
}
