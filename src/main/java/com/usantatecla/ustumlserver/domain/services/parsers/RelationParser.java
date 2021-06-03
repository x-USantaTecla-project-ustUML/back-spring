package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.AccountPersistenceMongodb;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Stack;


@AllArgsConstructor
@Data
public abstract class RelationParser {

    protected Member targetMember;
    protected String role;
    protected Stack<String> targetRoute;

    RelationParser() {
        this.role = "";
        this.targetRoute = new Stack<>();
    }

    protected Member getTarget(AccountPersistenceMongodb accountPersistence) {
        Account account = accountPersistence.read(this.targetRoute.pop());
        return this.getTargetMember(this.targetRoute, account.getProjects());
    }

    private Member getTargetMember(Stack<String> targetRoute, List<Project> projects) {
        for(Project projectItem : projects){
            if(projectItem.getName().equals(targetRoute.peek())){
                targetRoute.pop();
                if (targetRoute.size() == 0) {
                    return projectItem;
                } else return this.getTarget(targetRoute, projectItem.getMembers());
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    private Member getTarget(Stack<String> targetRoute, List<Member> members) {
        while (!targetRoute.isEmpty()) {
            for (Member memberItem : members) {
                if (!targetRoute.isEmpty() && memberItem.getName().equals(targetRoute.peek())) {
                    targetRoute.pop();
                    if (targetRoute.size() == 0) {
                        return memberItem;
                    } else this.getTarget(targetRoute, ((Package) memberItem).getMembers());
                }
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    protected void getRelationRole(Command relationCommand) {
        String role = relationCommand.getRelationRole();
        if (role != null) {
            this.role = role;
        }
    }

    public abstract RelationParser copy();

    public abstract Relation get(Command relationCommand, Member member, AccountPersistenceMongodb accountPersistence);
}
