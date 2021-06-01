package com.usantatecla.ustumlserver.domain.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class AccountBuilder {

    private BuilderContext context;
    private String id;
    private String name;
    private String email;
    private String password;
    private List<Project> projects;
    private ProjectBuilder projectBuilder;

    public AccountBuilder() {
        this.context = BuilderContext.ON_ACCOUNT;
        this.id = "id";
        this.name = "name";
        this.email = "a@a.es";
        this.password = new BCryptPasswordEncoder().encode("a");
        this.projects = new ArrayList<>();
    }

    public AccountBuilder(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.projects = account.getProjects();
    }

    public AccountBuilder name(String name) {
        switch (this.context) {
            case ON_ACCOUNT:
                this.name = name;
                break;
            case ON_PROJECT:
                this.projectBuilder.name(name);
                break;
        }
        return this;
    }

    public AccountBuilder project() {
        if (this.context == BuilderContext.ON_CLASS) {
            this.projects.add(this.projectBuilder.build());
        } else this.context = BuilderContext.ON_CLASS;
        this.projectBuilder = new ProjectBuilder();
        return this;
    }

    public Account build() {
        Account account = new Account(this.email, this.password, Role.AUTHENTICATED, this.projects);
        account.setId(this.id);
        account.setName(this.name);
        return account;
    }

}
