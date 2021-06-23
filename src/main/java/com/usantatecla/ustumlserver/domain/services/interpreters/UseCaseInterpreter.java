package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;
import com.usantatecla.ustumlserver.domain.persistence.ActorPersistence;
import com.usantatecla.ustumlserver.domain.persistence.UseCasePersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class UseCaseInterpreter extends MemberInterpreter{

    @Autowired
    private UseCasePersistence useCasePersistence;

    protected UseCaseInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void add(Command command) {
        super.add(command);
        UseCase useCase = (UseCase) this.member;
        this.addRelations(command);
        this.member = this.useCasePersistence.update(useCase);
    }

    @Override
    public void modify(Command command) {
        super.modify(command);
        UseCase useCase = (UseCase) this.member;
        this.modifyRelations(command);
        this.member = this.useCasePersistence.update(useCase);
    }

    @Override
    public void delete(Command command) {
        super.delete(command);
        this.member = this.useCasePersistence.deleteRelations(this.member, this.deleteRelations(command));
    }
}
