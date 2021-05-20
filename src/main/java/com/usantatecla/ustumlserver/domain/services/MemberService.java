package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;

abstract class MemberService {

    static final String RELATIONS_KEY = "relations";

    protected Member member;

    public MemberService(Member member) {
        this.member = member;
    }

    abstract Member add(Command command);

    protected void addRelations(Command command) {
        for (Command relationCommand : command.getCommands(MemberService.RELATIONS_KEY)) {
            this.member.addRelation(RelationType.get("use").create().get(relationCommand));
        }
    }
}
