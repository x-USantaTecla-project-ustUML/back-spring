package com.usantatecla.ustumlserver.domain.model.useCaseDiagram;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UseCase extends Member {

    private static final String UML_NAME = "usecase";

    public UseCase(String name) {
        super(name);
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
        return UseCase.UML_NAME + ":";
    }

    @Override
    public String getPlantUml() {
        return UseCase.UML_NAME;
    }
}
