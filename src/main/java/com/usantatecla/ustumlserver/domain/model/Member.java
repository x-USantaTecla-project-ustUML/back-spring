package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Member {

    static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + "([$_a-zA-Z]([$_a-zA-Z0-9]+)?))";

    protected String id;
    protected String name;

    public Member(String name) {
        this.name = name;
    }

    public static boolean matchesName(String name) {
        return name.matches(Member.NAME_REGEX);
    }

    public abstract String accept(Generator generator);

    public abstract void accept(MemberVisitor memberVisitor);

}
