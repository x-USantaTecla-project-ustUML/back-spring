package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberType;
import com.usantatecla.ustumlserver.domain.services.parsers.ProjectParser;
import com.usantatecla.ustumlserver.domain.services.reverseEngineering.GitRepositoryImporter;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountInterpreter extends WithMembersInterpreter {

    @Autowired
    private AccountPersistence accountPersistence;
    @Autowired
    private GitRepositoryImporter gitRepositoryImporter;

    public AccountInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void add(Command command) {
        super.add(command);
        Account account = (Account) this.member;
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            if (!projectCommand.has(MemberType.PROJECT.getName())) {
                throw new ServiceException(ErrorMessage.MEMBER_NOT_ALLOWED, projectCommand.getMemberType().getName());
            }
            account.add((Project) new ProjectParser().get(projectCommand));
        }
        this.member = this.accountPersistence.update(account);
    }

    @Override
    public void _import(Command command) {
        Account account = (Account) this.member;
        String url = command.getString(CommandType.IMPORT.getName());
        Project project = this.gitRepositoryImporter._import(url, account.getEmail());
        account.add(project);
        this.member = this.accountPersistence.update(account);
    }

    @Override
    Member find(String name) {
        return ((Account) this.member).find(name);
    }

}
