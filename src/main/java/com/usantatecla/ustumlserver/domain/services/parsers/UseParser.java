package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Use;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.Arrays;

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
        this.getTargetRoute(relationCommand);
        if (this.targetRoute.size() == 1) {

        } else {
            this.targetMember = this.getTarget();
        }
        use.setTarget(this.targetMember);
        //TODO role
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

}
