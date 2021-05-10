package com.usantatecla.ustumlserver.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Member {

    static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + "([$_a-zA-Z][$_a-zA-Z0-9]+))";

    protected String name;

    public abstract String accept(Generator generator);

}