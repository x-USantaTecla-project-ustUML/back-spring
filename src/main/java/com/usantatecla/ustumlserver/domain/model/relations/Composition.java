package com.usantatecla.ustumlserver.domain.model.relations;

import com.usantatecla.ustumlserver.domain.model.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@SuperBuilder
@Data
public class Composition extends Relation {

    private static final String UST_NAME = "composition:";
    private static final String PLANT_UML = "*-down->";

    public Composition(Member target, String role) {
        super(target, role);
    }

    @Override
    public void accept(RelationVisitor relationVisitor) {
        relationVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Composition.UST_NAME;
    }

    @Override
    public String getPlantUml() {
        return Composition.PLANT_UML;
    }

    @Override
    public Composition copy(Member target, String role) {
        return new Composition(target, role);
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
