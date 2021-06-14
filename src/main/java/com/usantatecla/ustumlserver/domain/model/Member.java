package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.generators.DirectoryTreeGenerator;
import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Member {

    static final String NAME_REGEX = "(" + Modifier.getNotAmongRegex() + "([$_a-zA-Z]([$_a-zA-Z0-9]+)?))";

    @EqualsAndHashCode.Exclude
    protected String id;
    protected String name;
    protected List<Relation> relations;

    protected Member(String name) {
        this.name = name;
        this.relations = new ArrayList<>();
    }

    public static boolean matchesName(String name) {
        return name.matches(Member.NAME_REGEX);
    }

    protected Stack<String> getStackRoute(String route) {
        Stack<String> stackPath = new Stack<>();
        List<String> splitPath = Arrays.asList(route.split("\\."));
        Collections.reverse(splitPath);
        stackPath.addAll(splitPath);
        return stackPath;
    }

    public Relation findRelation(String target) {
        for (Relation relation : this.getRelations()) {
            if (relation.getTarget().getName().equals(target)) {
                return relation;
            }
        }
        return null;
    }

    public abstract String accept(Generator generator);

    public abstract String accept(DirectoryTreeGenerator directoryTreeGenerator);

    public abstract void accept(MemberVisitor memberVisitor);

    public abstract String getUstName();

    public abstract String getPlantUml();

    public boolean isPackage() {
        return false;
    }

    public void addRelation(Relation relation) {
        this.relations.add(relation);
    }
}
