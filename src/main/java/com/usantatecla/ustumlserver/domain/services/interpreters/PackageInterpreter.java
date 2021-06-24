package com.usantatecla.ustumlserver.domain.services.interpreters;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;

public class PackageInterpreter extends WithMembersMemberInterpreter {

    public PackageInterpreter(Account account, Member member) {
        super(account, member);
    }

    @Override
    public void addCommandSections(Command command) {
        super.addCommandSections(command);
        Package pakage = (Package) this.member;
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            MemberParser memberParser = memberCommand.getMemberType().create(this.account);
            pakage.add(memberParser.get(memberCommand));
        }
    }

    @Override
    public boolean isInvalidAddKeys(Command command) {
        return super.isInvalidAddKeys(command) && !command.has(Command.MEMBERS);
    }

}
