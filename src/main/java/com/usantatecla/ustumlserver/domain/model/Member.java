package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Data
public abstract class Member {

    static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + "([$_a-zA-Z][$_a-zA-Z0-9]+))";

    protected String name;

    public abstract void accept(Generator generator);

}
