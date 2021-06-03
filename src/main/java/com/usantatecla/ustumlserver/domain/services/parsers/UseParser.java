package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Use;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UseParser extends RelationParser {

    @Override
    public RelationParser copy() {
        return new UseParser();
    }

    @Override
    public Use get(Command relationCommand, Member member, AccountPersistence accountPersistence) {
        Use use = new Use();
        this.getTargetRoute(relationCommand);
        if(relationCommand.has("role")) {
            this.getRelationRole(relationCommand);
        }
        if (this.targetRoute.size() == 1) {
            //TODO
        } else {
            this.targetMember = this.getTarget(accountPersistence);
        }
        use.setTarget(this.targetMember);
        use.setRole(this.role);
        return use;
    }

    private void getTargetRoute(Command relationCommand) {
        String name = relationCommand.getTargetName();
        if (name != null) {
            List<String> targetRoute = Arrays.asList(name.split("/"));
            if(!targetRoute.get(0).equals(this.getAuthenticatedEmail())){
                throw new ParserException(ErrorMessage.INVALID_ROUTE);
            }
            Collections.reverse(targetRoute);
            this.targetRoute.addAll(targetRoute);
        } else {
            throw new ParserException(ErrorMessage.INVALID_NAME, name);
        }
    }

    String getAuthenticatedEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
