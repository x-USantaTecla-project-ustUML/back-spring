package com.usantatecla.ustumlserver.domain.model;

import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Definition {

    protected String name;
    protected String type;
    protected List<Modifier> modifiers;

}
