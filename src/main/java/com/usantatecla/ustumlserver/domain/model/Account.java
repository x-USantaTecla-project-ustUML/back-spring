package com.usantatecla.ustumlserver.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends Member {
    private String email;
    private String password;
    private Role role;
    private List<Project> projects;

    public void add(Project project) {
        this.projects.add(project);
    }

    public Project find(String name) {
        for(Project project: this.projects) {
            if (project.getName().equals(name)){
                return project;
            }
        }
        return null;
    }

    @Override
    public String accept(Generator generator) {
        return null;
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {

    }
}
