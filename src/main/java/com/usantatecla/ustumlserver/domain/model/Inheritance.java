package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Inheritance extends Relation {

    private static final String UST_NAME = "inheritance:";
    private static final String PLANT_UML = "-down-|>";

    public Inheritance(Member target, String role) {
        super(target, role);
    }

    @Override
    public void accept(RelationVisitor relationVisitor) {
        relationVisitor.visit(this);
    }


    @Override
    public String getUstName() {
        return Inheritance.UST_NAME;
    }

    @Override
    public String getPlantUml() {
        return Inheritance.PLANT_UML;
    }

    @Override
    public Relation copy(Member target, String role) {
        return new Inheritance(target, role);
    }
}
