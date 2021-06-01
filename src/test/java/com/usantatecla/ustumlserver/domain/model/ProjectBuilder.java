package com.usantatecla.ustumlserver.domain.model;

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
        Project project = new Project(this.name, this.members);
        project.setId(this.id);
        return project;
    }
}
