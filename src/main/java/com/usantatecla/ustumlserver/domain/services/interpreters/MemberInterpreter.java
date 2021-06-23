package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.persistence.MemberPersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.parsers.ParserException;
import com.usantatecla.ustumlserver.domain.services.parsers.RelationParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MemberInterpreter {

    @Autowired
    protected MemberPersistence memberPersistence;
    protected Account account;
    protected Member member;

    protected MemberInterpreter(Account account, Member member) {
        this.account = account;
        this.member = member;
    }

    public void add(Command command) {
        if (this.isInvalidAddKeys(command)) {
            throw new ParserException(ErrorMessage.INVALID_COMMAND_KEYS);
        }
        this.addCommandSections(command);
        this.member = this.memberPersistence.update(member);
    }

    protected boolean isInvalidAddKeys(Command command) {
        return !command.has(Command.RELATIONS);
    }

    protected void addCommandSections(Command command) {
        for (Command relationCommand : command.getCommands(Command.RELATIONS)) {
            this.member.add(new RelationParser().get(this.account, relationCommand));
        }
    }

    public void delete(Command command) {
        if (this.isInvalidDeleteKeys(command)) {
            throw new ParserException(ErrorMessage.INVALID_COMMAND_KEYS);
        }
        this.deleteCommandSections(command);
    }

    protected boolean isInvalidDeleteKeys(Command command) {
        return !command.has(Command.RELATIONS);
    }

    protected void deleteCommandSections(Command command) {
        List<Relation> relations = new ArrayList<>();
        for (Command relationCommand : command.getCommands(Command.RELATIONS)) {
            String targetRoute = relationCommand.getTargetName();
            Member target = this.account.findRoute(targetRoute);
            if (target == null) {
                throw new ServiceException(ErrorMessage.INVALID_ROUTE, targetRoute);
            }
            relations.add(this.member.deleteRelation(target));
        }
        this.member = this.memberPersistence.deleteRelations(this.member, relations);
    }

    public void modify(Command command) {
        if (this.isInvalidModifyKeys(command)) {
            throw new ParserException(ErrorMessage.INVALID_COMMAND_KEYS);
        }
        this.modifyCommandSections(command);
        this.member = this.memberPersistence.update(member);
    }

    protected boolean isInvalidModifyKeys(Command command) {
        return !command.has(Command.RELATIONS);
    }

    protected void modifyCommandSections(Command command) {
        for (Command relationCommand : command.getCommands(Command.RELATIONS)) {
            this.member.modify(new RelationParser().get(this.account, relationCommand),
                    new RelationParser().getModifiedRelation(this.account, relationCommand));
        }
    }

    public Member open(Command command) {
        throw new ServiceException(ErrorMessage.OPEN_NOT_ALLOWED);
    }

    public void _import(Command command) {
        throw new ServiceException(ErrorMessage.IMPORT_NOT_ALLOWED);
    }

    public Member getMember() {
        return this.member;
    }

}
