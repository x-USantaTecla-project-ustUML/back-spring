package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.persistence.ActorPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ActorInterpreter extends MemberInterpreter{

    @Autowired
    private ActorPersistence actorPersistence;

    protected ActorInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void add(Command command) {
        super.add(command);
        Actor actor = (Actor) this.member;
        this.addRelations(command);
        this.member = this.actorPersistence.update(actor);
    }

    @Override
    public void modify(Command command) {
        super.modify(command);
        Actor actor = (Actor) this.member;
        this.modifyRelations(command);
        this.member = this.actorPersistence.update(actor);
    }

    @Override
    public void delete(Command command) {
        super.delete(command);
        this.member = this.actorPersistence.deleteRelations(this.member, this.deleteRelations(command));
    }
}
