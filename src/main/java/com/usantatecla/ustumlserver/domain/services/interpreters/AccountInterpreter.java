package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.parsers.PackageParser;
import com.usantatecla.ustumlserver.domain.services.parsers.ProjectParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountInterpreter extends MemberInterpreter{

    @Autowired
    private AccountPersistence accountPersistence;

    public AccountInterpreter(Member member) {
        super(member);
    }

    @Override
    public void add(Command command) {
        Account account = (Account) this.member;
        for(Command projectCommand: command.getCommands("members")) {
            account.add(new ProjectParser().get(projectCommand));
        }
        this.accountPersistence.update(account);
    }

    public Member open(Command command) {
        String name = command.getString(CommandType.OPEN.getName());
        Member member = ((Account)this.member).find(name);
        if (member == null) {
            throw new ServiceException(ErrorMessage.MEMBER_NOT_FOUND, name);
        }
        return member;
    }

    @Override
    public void accept(InterpreterVisitor interpreterVisitor) {
        interpreterVisitor.visit(this);
    }
}
