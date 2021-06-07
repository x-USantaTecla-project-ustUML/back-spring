package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public abstract class Relation {

    protected String id;
    private Member target;
    private String role;
    private String targetRoute;

    public Relation(Member target, String role, String targetRoute) {
        this.target = target;
        if (role != null) {
            this.role = role;
        } else {
            this.role = "";
        }
        this.targetRoute = targetRoute;
    }

    public abstract void accept(RelationVisitor relationVisitor);

    public String accept(Generator generator, Member origin) {
        return generator.visit(this, origin);
    }

    public abstract String getUstName();

    public abstract String getPlantUml();

    public abstract Relation copy(Member target, String role, String targetRoute);

}
