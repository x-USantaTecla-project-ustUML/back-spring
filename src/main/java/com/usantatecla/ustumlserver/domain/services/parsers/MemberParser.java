package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

public abstract class MemberParser {

    protected String name;

    public abstract Member get(Command command);

    public void addRelation(Member member, Command command, Account account) {
        for (Command relationCommand : command.getCommands(Command.RELATIONS)) {
            member.addRelation(new RelationParser().get(account, relationCommand));
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
