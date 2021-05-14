package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.persistence.PackagePersistence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Member {

    static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + "(([$_a-zA-Z]([$_a-zA-Z0-9])?)+))";

    protected String name;

    public static boolean matchesName(String name) {
        return name.matches(Member.NAME_REGEX);
    }

    public abstract String accept(Generator generator);

    public abstract void accept(PackagePersistence packagePersistence);

}
