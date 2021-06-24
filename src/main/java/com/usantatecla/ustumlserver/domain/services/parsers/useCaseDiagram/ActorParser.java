package com.usantatecla.ustumlserver.domain.services.parsers.useCaseDiagram;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ActorParser extends MemberParser {

    public ActorParser(Account account) {
        super(account);
    }

    @Override
    public Member get(Command command) {
        this.parseName(command.getMemberName());
        Actor actor = this.createActor();
        this.addRelations(command, actor);
        return actor;
    }

    protected Actor createActor() {
        return new Actor(this.name);
    }

    @Override
    public MemberParser copy(Account account) {
        return new ActorParser(account);
    }

}
