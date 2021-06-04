package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.persistence.AccountPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


@AllArgsConstructor
@Data
public class RelationParser {

    protected Member targetMember;
    protected String role;
    protected Stack<String> targetRoute;

    RelationParser() {
        this.role = "";
        this.targetRoute = new Stack<>();
    }

    protected Relation get(Relation relation, Command relationCommand, Account account) {
        this.setTargetRoute(relationCommand);
        if (relationCommand.has("role")) {
            this.setRelationRole(relationCommand);
        }
        this.targetMember = this.getTarget(account);
        relation.setTarget(this.targetMember);
        relation.setRole(this.role);
        return relation;
    }

    protected Member getTarget(Account account) {
        for (Project projectItem : account.getProjects()) {
            if (projectItem.getName().equals(targetRoute.peek())) {
                targetRoute.pop();
                if (targetRoute.size() == 0) {
                    return projectItem;
                } else return this.getMemberTarget(targetRoute, projectItem.getMembers());
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    private Member getMemberTarget(Stack<String> targetRoute, List<Member> members) {
        Member member = null;
        for (Member memberItem : members) {
            if (!targetRoute.isEmpty() && memberItem.getName().equals(targetRoute.peek())) {
                targetRoute.pop();
                if (targetRoute.size() == 0) {
                    return memberItem;
                } else member = this.getMemberTarget(targetRoute, ((Package) memberItem).getMembers());
            }
        }
        if (member == null) {
            throw new ParserException(ErrorMessage.INVALID_ROUTE);
        }
        return member;
    }

    protected void setRelationRole(Command relationCommand) {
        String role = relationCommand.getRelationRole();
        if (role != null) {
            this.role = role;
        }
    }

    protected void setTargetRoute(Command relationCommand) {
        String name = relationCommand.getTargetName(relationCommand.getRelationType().getName());
        if (name != null) {
            List<String> targetRoute = Arrays.asList(name.split("\\."));
            Collections.reverse(targetRoute);
            this.targetRoute.addAll(targetRoute);
        } else {
            throw new ParserException(ErrorMessage.INVALID_NAME, name);
        }
    }

}
