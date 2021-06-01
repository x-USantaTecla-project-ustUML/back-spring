package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackageBuilder {

    private BuilderContext context;
    protected String id;
    protected String name;
    protected List<Member> members;
    private ClassBuilder classBuilder;
    private PackageBuilder packageBuilder;

    public PackageBuilder() {
        this.context = BuilderContext.ON_ME;
        this.name = "name";
        this.members = new ArrayList<>();
    }

    public PackageBuilder id(String id) {
        switch (this.context) {
            case ON_ME:
                this.id = id;
                break;
            case ON_PACKAGE:
                this.packageBuilder.id(id);
                break;
            case ON_CLASS:
                this.classBuilder.id(id);
                break;
        }
        return this;
    }

    public PackageBuilder name(String name) {
        switch (this.context) {
            case ON_ME:
                this.name = name;
                break;
            case ON_PACKAGE:
                this.packageBuilder.name(name);
                break;
            case ON_CLASS:
                this.classBuilder.name(name);
                break;
        }
        return this;
    }

    public PackageBuilder pakage(Package pakage) {
        this.members.add(pakage);
        return this;
    }

    public PackageBuilder pakage() {
        if (this.context == BuilderContext.ON_PACKAGE) {
            this.members.add(this.packageBuilder.build());
        } else this.context = BuilderContext.ON_PACKAGE;
        this.packageBuilder = new PackageBuilder();
        return this;
    }

    public PackageBuilder clazz() {
        if (this.context == BuilderContext.ON_CLASS) {
            this.members.add(this.classBuilder.build());
        } else this.context = BuilderContext.ON_CLASS;
        this.classBuilder = new ClassBuilder();
        return this;
    }

    public PackageBuilder classes(Class... classes) {
        assert this.context != BuilderContext.ON_CLASS;

        this.members.addAll(Arrays.asList(classes));
        return this;
    }

    public Package build() {
        if (this.packageBuilder != null) {
            this.members.add(this.packageBuilder.build());
        }
        if (this.classBuilder != null) {
            this.members.add(this.classBuilder.build());
        }
        Package myPackage = new Package(this.name, this.members);
        myPackage.setId(this.id);
        return myPackage;
    }

}
