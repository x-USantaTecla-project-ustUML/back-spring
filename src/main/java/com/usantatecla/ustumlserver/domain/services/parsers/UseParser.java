package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Use;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.Arrays;

public class UseParser extends RelationParser {

    @Override
    public RelationParser copy() {
        return this;
    }

    @Override
    public Use get(Command relationCommand, Member member) {
        Use use = new Use();
        this.getTargetRoute(relationCommand);
        this.getRelationRole(relationCommand);
        if (this.targetRoute.size() == 1) {
            //TODO
        } else {
            this.targetMember = this.getTarget();
        }
        use.setTarget(this.targetMember);
        use.setRole(this.role);
        return use;
    }

    private void getTargetRoute(Command relationCommand) {
        String name = relationCommand.getTargetName();
        if (!name.equals("null") && Member.matchesName(name)) {
            this.targetRoute.addAll(Arrays.asList(name.split(".")));
        } else {
            throw new ParserException(ErrorMessage.INVALID_NAME, name);
        }
    }

    private void getRelationRole(Command relationCommand) {
        String role = relationCommand.getRelationRole();
        if (!role.equals("null")) {
            this.role = role;
        } else {
            this.role = "";
        }
    }

}
