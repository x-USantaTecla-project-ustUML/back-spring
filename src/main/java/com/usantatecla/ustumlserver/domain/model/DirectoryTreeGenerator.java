package com.usantatecla.ustumlserver.domain.model;

import java.util.StringJoiner;

public class DirectoryTreeGenerator {
    protected static final String TAB_CHAR = "  "; // 2 o 4 espacios
    protected static final String EOL_CHAR = "\n";

    public String generate(Member member) {
        return "{" + member.accept(this) + "}";
    }

    String visit(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.merge(new StringJoiner(" ").add("\"name\": ")
                .add("\"" + account.getName() + "\""));
        if (account.getProjects().size() != 0) {
            stringJoiner.add(",\"children\": [");
            for (Member member : account.getProjects()) {
                stringJoiner.add("{\"name\": \"" + member.getName() + "\"}");
                if (!member.equals(account.getProjects().get(account.getProjects().size() - 1))) {
                    stringJoiner.add(",");
                }
            }
            stringJoiner.add("]");
        }
        return stringJoiner.toString();
    }

    String visit(Package pakage) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.merge(new StringJoiner(" ").add("\"name\": ")
                .add("\"" + pakage.getName() + "\""));
        if (pakage.getMembers().size() != 0) {
            stringJoiner.add(",\"children\": [");
            for (Member member : pakage.getMembers()) {
                stringJoiner.add("{" + member.accept(this) + "}");
                if (!member.getId().equals(pakage.getMembers().get(pakage.getMembers().size() - 1).getId())) {
                    stringJoiner.add(",");
                }
            }
            stringJoiner.add("]");
        }
        return stringJoiner.toString();
    }

    String visit(Class clazz) {
        return "";
    }
}
