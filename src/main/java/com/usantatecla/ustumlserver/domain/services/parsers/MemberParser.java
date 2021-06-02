package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.AccountPersistenceMongodb;

public abstract class MemberParser {

    protected String name;

    public abstract Member get(Command command);

    public void addRelation(Member member, Command command, AccountPersistenceMongodb accountPersistence) {
        for (Command relationCommand : command.getCommands(Command.RELATIONS)) {
            RelationType relationType = relationCommand.getRelationType();
            member.addRelation(relationType.create().get(relationCommand, member, accountPersistence));
        }
    }

    protected void parseName(Command command) {
        String name = command.getMemberName();
        if (!name.equals("null") && Member.matchesName(name)) {
            this.name = name;
        } else {
            throw new ParserException(ErrorMessage.INVALID_NAME, name);
        }
    }

    public abstract MemberParser copy();

}
