package com.usantatecla.ustumlserver.domain.model.relations;

import com.usantatecla.ustumlserver.domain.model.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public class Inheritance extends Relation {

    private static final String UST_NAME = "inheritance:";
    private static final String PLANT_UML = "-up-|>";

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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
