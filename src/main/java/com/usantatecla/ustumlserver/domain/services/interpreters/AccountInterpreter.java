package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.domain.services.ServiceException;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberParser;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberType;
import com.usantatecla.ustumlserver.domain.services.parsers.ProjectParser;
import com.usantatecla.ustumlserver.domain.services.reverseEngineering.GitRepositoryImporter;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.CommandType;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AccountInterpreter extends WithMembersInterpreter {

    @Autowired
    private AccountPersistence accountPersistence;
    @Autowired
    private GitRepositoryImporter gitRepositoryImporter;

    public AccountInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void addCommandSections(Command command) {
        super.addCommandSections(command);
        Account account = (Account) this.member;
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            if (!projectCommand.has(MemberType.PROJECT.getName())) {
                throw new ServiceException(ErrorMessage.MEMBER_NOT_ALLOWED, projectCommand.getMemberType().getName());
            }
            account.add((Project) new ProjectParser(this.account).get(projectCommand));
        }
    }

    @Override
    public boolean isInvalidAddKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public void deleteCommandSections(Command command) {
        super.deleteCommandSections(command);
        Account account = (Account) this.member;
        List<Member> members = new ArrayList<>();
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            members.add(account.deleteMember(projectCommand.getMemberName()));
        }
        this.member = this.accountPersistence.deleteMembers(account, members);
    }

    @Override
    public boolean isInvalidDeleteKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public void modifyCommandSections(Command command) {
        super.modifyCommandSections(command);
        Account account = (Account) this.member;
        for (Command projectCommand : command.getCommands(Command.MEMBERS)) {
            if (!projectCommand.has(MemberType.PROJECT.getName())) {
                throw new ServiceException(ErrorMessage.MEMBER_NOT_ALLOWED, projectCommand.getMemberType().getName());
            }
            account.modify(projectCommand.getMemberName(), projectCommand.getString(MemberParser.SET_KEY));
        }
    }

    @Override
    public boolean isInvalidModifyKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

    @Override
    public void _import(Command command) {
        Account account = (Account) this.member;
        String url = command.getString(CommandType.IMPORT.getName());
        Project project = this.gitRepositoryImporter._import(url, account.getEmail());
        account.add(project);
        this.member = this.memberPersistence.update(account);
    }

}
