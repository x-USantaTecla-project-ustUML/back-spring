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

    protected Relation get(Relation relation, Command relationCommand, AccountPersistence accountPersistence) {
        this.getTargetRoute(relationCommand);
        if (relationCommand.has("role")) {
            this.getRelationRole(relationCommand);
        }
        this.targetMember = this.getTarget(accountPersistence);
        relation.setTarget(this.targetMember);
        relation.setRole(this.role);
        return relation;
    }

    protected Member getTarget(AccountPersistence accountPersistence) {
        Account account = accountPersistence.read(this.targetRoute.pop());
        return this.getTargetMember(this.targetRoute, account.getProjects());
    }

    private Member getTargetMember(Stack<String> targetRoute, List<Project> projects) {
        for (Project projectItem : projects) {
            if (projectItem.getName().equals(targetRoute.peek())) {
                targetRoute.pop();
                if (targetRoute.size() == 0) {
                    return projectItem;
                } else return this.getTarget(targetRoute, projectItem.getMembers());
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    private Member getTarget(Stack<String> targetRoute, List<Member> members) {
        Member member = null;
        for (Member memberItem : members) {
            if (!targetRoute.isEmpty() && memberItem.getName().equals(targetRoute.peek())) {
                targetRoute.pop();
                if (targetRoute.size() == 0) {
                    return memberItem;
                } else member = this.getTarget(targetRoute, ((Package) memberItem).getMembers());
            }
        }
        if (member == null) {
            throw new ParserException(ErrorMessage.INVALID_ROUTE);
        }
        return member;
    }

    protected void getRelationRole(Command relationCommand) {
        String role = relationCommand.getRelationRole();
        if (role != null) {
            this.role = role;
        }
    }

    protected void getTargetRoute(Command relationCommand) {
        String name = relationCommand.getTargetName();
        if (name != null) {
            List<String> targetRoute = Arrays.asList(name.split("/"));
            if (!targetRoute.get(0).equals(this.getAuthenticatedEmail())) {
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
