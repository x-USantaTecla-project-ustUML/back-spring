package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.persistence.MemberPersistence;
import org.springframework.beans.factory.annotation.Autowired;

abstract class RelationParser {

    static final String ROLE_KEY = "role";

    protected Member target;
    protected String role;

    @Autowired
    private MemberPersistence memberPersistence;

    public Relation get(Command command) {
        String targetName = this.getTargetName(command);
        this.target = this.memberPersistence.read(targetName);
        if (this.target == null) {
            throw new CommandParserException(Error.MEMBER_NOT_FOUND, targetName);
        }
        this.role = command.getString(RelationParser.ROLE_KEY);
        return this.create();
    }

    protected abstract String getTargetName(Command command);

    protected abstract Relation create();

    public abstract RelationParser copy();

}
