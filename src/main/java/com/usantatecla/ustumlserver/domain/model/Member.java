package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Data
public abstract class Member {

    static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + "([$_a-zA-Z][$_a-zA-Z0-9]+))";

    protected String name;

    Member(String name) {
        this.name = name;
    }

    public abstract void accept(Generator generator);

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Member other = (Member) obj;
        if (this.name == null) {
            return other.getName() == null;
        } else return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
