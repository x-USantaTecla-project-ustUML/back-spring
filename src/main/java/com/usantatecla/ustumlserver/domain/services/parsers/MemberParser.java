package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.services.parsers.relations.RelationParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class MemberParser {

    public static final String SET_KEY = "set";

    protected Account account;
    protected String name;

    protected MemberParser(Account account) {
        this.account = account;
    }

    public abstract Member get(Command command);

    protected void parseName(String name) {
        if (!name.equals("null") && Member.matchesName(name)) {
            this.name = name;
        } else {
            throw new ParserException(ErrorMessage.INVALID_NAME, name);
        }
    }

    protected void addRelations(Command command, Member member) {
        for (Command relationCommand : command.getCommands(Command.RELATIONS)) {
            member.add(new RelationParser().get(this.account, relationCommand));
        }
    }

    public abstract MemberParser copy(Account account);

}
