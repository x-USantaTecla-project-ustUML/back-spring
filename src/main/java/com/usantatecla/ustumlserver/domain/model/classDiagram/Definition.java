package com.usantatecla.ustumlserver.domain.model.classDiagram;

import com.usantatecla.ustumlserver.domain.model.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Definition {

    public static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + Member.NAME_REGEX + ")";
    public static final String TYPE_REGEX = "(" + Modifier.getNotAmongRegex() + "([$_a-zA-Z]([$_a-zA-Z0-9\\<\\>\\[\\]]+)?))";

    protected String name;
    protected String type;
    protected List<Modifier> modifiers;

    public Definition(String name, String type, List<Modifier> modifiers) {
        this.name = name;
        this.type = type;
        if (!Modifier.isThereVisibility(modifiers)) {
            modifiers.add(0, Modifier.PACKAGE);
        }
        this.modifiers = modifiers;
    }

}
