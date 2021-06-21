package com.usantatecla.ustumlserver.domain.model.classDiagram;

import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import com.usantatecla.ustumlserver.domain.model.ModelException;
import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Enum extends Class {

    private static final String UML_NAME = "enum";

    private List<String> objects;

    public Enum(String name, List<Modifier> modifiers, List<Attribute> attributes) {
        super(name, modifiers, attributes);
        this.objects = new ArrayList<>();
    }

    public void addObject(String object) {
        if (this.findObject(object)) {
            throw new ModelException(ErrorMessage.OBJECT_ALREADY_EXISTS, object);
        }
        this.objects.add(object);
    }

    private boolean findObject(String object) {
        for (String actualObject : this.objects) {
            if (actualObject.equals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String accept(Generator generator) {
        return generator.visit(this);
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {
        memberVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Enum.UML_NAME + ":";
    }

    @Override
    public String getPlantUml() {
        return Enum.UML_NAME;
    }

}
