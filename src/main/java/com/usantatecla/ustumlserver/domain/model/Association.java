package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Association extends Relation{

    private static final String UST_NAME = "association:";
    private static final String PLANT_UML = "-down->";

    public Association(Member target, String role) {
        super(target, role);
    }

    @Override
    public void accept(RelationVisitor relationVisitor) {
        relationVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Association.UST_NAME;
    }

    @Override
    public String getPlantUml() {
        return Association.PLANT_UML;
    }

    @Override
    public Association copy(Member target, String role) {
        return new Association(target, role);
    }
}
