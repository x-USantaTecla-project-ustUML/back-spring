package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Use extends Relation {

    private static final String UST_NAME = "use:";
    private static final String PLANT_UML = ".down.>";

    public Use(Member target, String role) {
        super(target, role);
    }

    @Override
    public void accept(RelationVisitor relationVisitor) {
        relationVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Use.UST_NAME;
    }

    @Override
    public String getPlantUml() {
        return Use.PLANT_UML;
    }

    @Override
    public Use copy(Member target, String role) {
        return new Use(target, role);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
