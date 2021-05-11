package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.List;

public class PackageBuilder {

    private BuilderContext context;
    private String name;
    private List<Member> members;
    private ClassBuilder classBuilder;

    public PackageBuilder() {
        this.context = BuilderContext.ON_PACKAGE;
        this.name = "name";
        this.members = new ArrayList<>();
    }

    public PackageBuilder name(String name) {
        switch (this.context) {
            case ON_PACKAGE:
                this.name = name;
                break;
            case ON_CLASS:
                this.classBuilder.name(name);
                break;
        }
        return this;
    }

    public PackageBuilder clazz() {
        if (this.context == BuilderContext.ON_CLASS) {
            this.members.add(this.classBuilder.build());
        } else this.context = BuilderContext.ON_CLASS;
        this.classBuilder = new ClassBuilder();
        return this;
    }

    public Package build(){
        if (this.classBuilder != null) {
            this.members.add(this.classBuilder.build());
        }
        return new Package(this.name, this.members);
    }

}
