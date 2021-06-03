package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;

public class ProjectBuilder extends PackageBuilder {

    @Override
    public ProjectBuilder id(String id) {
        super.id(id);
        return this;
    }

    @Override
    public ProjectBuilder name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public ProjectBuilder pakage(Package pakage) {
        super.pakage(pakage);
        return this;
    }

    @Override
    public ProjectBuilder pakage() {
        super.pakage();
        return this;
    }

    @Override
    public ProjectBuilder clazz() {
        super.clazz();
        return this;
    }

    @Override
    public ProjectBuilder classes(Class... classes) {
        super.classes(classes);
        return this;
    }

    @Override
    public Project build() {
        super.build();
        Project project = new Project(this.name, this.members);
        project.setId(this.id);
        return project;
    }
}
