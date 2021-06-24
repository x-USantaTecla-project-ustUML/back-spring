package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
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

    public static final String NAME_REGEX = "([$_a-zA-Z]([$_a-zA-Z0-9]+)?)";

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

    protected Deque<String> getStackRoute(String route) {
        return new ArrayDeque<>(Arrays.asList(route.split("\\.")));
    }

    public abstract String accept(Generator generator);

    public abstract void accept(MemberVisitor memberVisitor);

    public abstract String getUstName();

    public abstract String getPlantUml();

    public boolean isPackage() {
        return false;
    }

    public void add(Relation relation) {
        this.relations.add(relation);
    }

    public void modify(Relation relation, Relation modifiedRelation) {
        if (!this.relations.contains(relation)) {
            throw new ParserException(ErrorMessage.RELATION_NOT_FOUND, relation.toString());
        }
        this.relations.remove(relation);
        this.relations.add(modifiedRelation);
    }

    public Relation deleteRelation(Member target) {
        Relation relation = this.findRelation(target);
        if (relation == null) {
            throw new ParserException(ErrorMessage.RELATION_NOT_FOUND, target.getName());
        }
        this.relations.remove(relation);
        return relation;
    }

    public Relation findRelation(Member target) {
        for (Relation relation : this.relations) {
            if (relation.getTargetId().equals(target.getId())) {
                return relation;
            }
        }
        return null;
    }

}
