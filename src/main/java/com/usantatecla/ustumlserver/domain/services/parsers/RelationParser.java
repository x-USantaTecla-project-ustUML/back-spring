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
        Project project = this.getProject(this.targetRoute.pop(), account.getProjects());
        return this.getTargetMember(this.targetRoute, project.getPackageMembers());
    }

    private Member getTargetMember(Stack<String> targetRoute, List<Package> packages) {
        while (!targetRoute.isEmpty()) {
            for (Member memberItem : packages) {
                if (!targetRoute.isEmpty() && memberItem.getName().equals(targetRoute.peek())) {
                    targetRoute.pop();
                    if (targetRoute.size() == 0) {
                        return memberItem;
                    } else this.getTargetMember(targetRoute, ((Package) memberItem).getPackageMembers());
                }
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    private Project getProject(String projectName, List<Project> projects) {
        for (Project projectItem : projects) {
            if (projectItem.getName().equals(projectName)) {
                return projectItem;
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    public abstract RelationParser copy();

    public abstract Relation get(Command relationCommand, Member member, AccountPersistenceMongodb accountPersistence);
}
