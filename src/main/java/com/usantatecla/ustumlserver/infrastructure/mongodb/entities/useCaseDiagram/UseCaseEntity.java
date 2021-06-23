package com.usantatecla.ustumlserver.infrastructure.mongodb.entities.useCaseDiagram;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@SuperBuilder
@AllArgsConstructor
@Document
public class UseCaseEntity extends MemberEntity {

    public UseCaseEntity(UseCase useCase) {
        BeanUtils.copyProperties(useCase, this);
    }

    @Override
    protected Member toMember() {
        return this.toUseCase();
    }

    @Override
    public Member toMemberWithoutRelations() {
        UseCase useCase = this.createUseCase();
        BeanUtils.copyProperties(this, useCase);
        useCase.setRelations(new ArrayList<>());
        return useCase;
    }

    protected UseCase createUseCase() {
        return new UseCase();
    }

    public UseCase toUseCase() {
        UseCase useCase = (UseCase) this.toMemberWithoutRelations();
        useCase.setRelations(this.getRelations());
        return useCase;
    }
}
