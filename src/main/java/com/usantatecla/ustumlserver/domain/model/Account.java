package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SuperBuilder
@Data
@AllArgsConstructor
public class Account extends Member implements WithMembersMember {

    private static final String UST_NAME = "account:";
    private static final String PLANT_UML = "account";

    private String email;
    private String password;
    private Role role;
    private List<Project> projects;

    public Account() {
        this.projects = new ArrayList<>();
    }

    public void add(Project project) {
        if (this.find(project.getName()) != null) {
            throw new ModelException(ErrorMessage.MEMBER_ALREADY_EXISTS, project.getName());
        }
        this.projects.add(project);
    }

    @Override
    public Project find(String name) {
        for (Project project : this.projects) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }

    @Override
    public Member findRoute(String route) {
        Stack<String> stackRoute = this.getStackRoute(route);
        Project project = this.find(stackRoute.pop());
        if (!stackRoute.isEmpty() && project != null) {
            return project.findRoute(stackRoute);
        }
        return project;
    }

    @Override
    public String accept(Generator generator) {
        return generator.visit(this);
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {
        memberVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Account.UST_NAME;
    }

    @Override
    public String getPlantUml() {
        return Account.PLANT_UML;
    }
}
