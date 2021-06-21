package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class PackageParser extends MemberParser {

    protected List<Member> members;

    public PackageParser(Account account) {
        super(account);
        this.members = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        this.parseName(command.getMemberName());
        Package pakage = this.createPackage();
        for (Command memberCommand : command.getCommands(Command.MEMBERS)) {
            MemberParser memberParser = memberCommand.getMemberType().create(this.account);
            Member member = memberParser.get(memberCommand);
            pakage.add(member);
        }
        this.addRelations(command, pakage);
        return pakage;
    }

    protected Package createPackage() {
        return new Package(this.name, this.members);
    }

    @Override
    public PackageParser copy(Account account) {
        return new PackageParser(account);
    }

}
