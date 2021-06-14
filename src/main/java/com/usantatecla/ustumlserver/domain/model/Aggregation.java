package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Aggregation extends Relation {

    private static final String UST_NAME = "aggregation:";
    private static final String PLANT_UML = "o-down->";

    public Aggregation(Member target, String role) {
        super(target, role);
    }

    @Override
    public void accept(RelationVisitor relationVisitor) {
        relationVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Aggregation.UST_NAME;
    }

    @Override
    public String getPlantUml() {
        return Aggregation.PLANT_UML;
    }

    @Override
    public Aggregation copy(Member target, String role) {
        return new Aggregation(target, role);
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
