package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Use;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

public class UseParser extends RelationParser {

    private static final String USE = "use:";

    @Override
    public RelationParser copy() {
        return this;
    }

    //TODO mirar si el target existe o no
    @Override
    public Use get(Command relationCommand) {
        Use use = new Use();
        this.targetName(relationCommand);

        return null;
    }

    protected Use createUse() {
        //return new Use(this.targetName, this.role);
        return null;
    }

    private void targetName(Command relationCommand) {
        String name = relationCommand.getTargetName();
        if (!name.equals("null") && Member.matchesName(name)) {
            this.targetName = name;
        } else {
            throw new ParserException(ErrorMessage.INVALID_NAME, name);
        }
    }

}
