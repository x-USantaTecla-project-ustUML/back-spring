package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import org.springframework.stereotype.Service;

@Service
abstract class MemberService {

    protected Member member;

    public MemberService(Member member) {
        this.member = member;
    }

    abstract Member add(Command command);

    public abstract MemberService copy();

    public abstract Member get(Command command);

    protected String getName(Command command) {
        String name = command.getMemberName();
        if (Package.matchesName(name)) {
            return name;
        } else {
            throw new CommandParserException(Error.INVALID_NAME, name);
        }
    }

}
