package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Member {

    static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + "([$_a-zA-Z]([$_a-zA-Z0-9]+)?))";

    protected String id;
    protected String name;
    protected List<Relation> relations;

    protected Member(String name) {
        this.name = name;
    }

    public static boolean matchesName(String name) {
        return name.matches(Member.NAME_REGEX);
    }

    public abstract String accept(Generator generator);

    public abstract String accept(DirectoryTreeGenerator directoryTreeGenerator);

    public abstract void accept(MemberVisitor memberVisitor);

    public abstract String getUstName();

    public boolean isPackage() {
        return false;
    }

    public void addRelation(Relation relation) {
        this.relations.add(relation);
    }
}
