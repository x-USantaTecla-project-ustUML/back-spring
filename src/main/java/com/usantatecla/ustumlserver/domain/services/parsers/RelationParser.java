package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.AccountPersistenceMongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


@AllArgsConstructor
@Data
public abstract class RelationParser {

    protected Member targetMember;
    protected String role;
    protected Stack<String> targetRoute;

    RelationParser(){
        this.targetRoute = new Stack<>();
    }

    protected Member getTarget(AccountPersistenceMongodb accountPersistence) {
        Account account = accountPersistence.read(this.targetRoute.pop());
        Project project = this.getProject(this.targetRoute.pop(), account.getProjects());
        return this.getTargetMember(this.targetRoute, project.getPackageMembers());
    }

    private Member getTargetMember(Stack<String> targetRoute, List<Package> packages) {
        while (!targetRoute.empty()) {
            if(targetRoute.size() == 1) {
                for (Member memberItem: packages) {
                    if (memberItem.getName().equals(targetRoute.peek())) {
                        targetRoute.pop();
                        return memberItem;
                    }
                }
            } else {
                for (Package memberItem: packages) {
                    if (memberItem.getName().equals(targetRoute.pop())) {
                        this.getTargetMember(targetRoute, memberItem.getPackageMembers());
                    }
                }
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    private Project getProject(String projectName, List<Project> projects) {
        for (Project projectItem: projects) {
            if (projectItem.getName().equals(projectName)) {
                return projectItem;
            }
        }
        throw new ParserException(ErrorMessage.INVALID_ROUTE);
    }

    public abstract RelationParser copy();

    public abstract Relation get(Command relationCommand, Member member, AccountPersistenceMongodb accountPersistence);
}
