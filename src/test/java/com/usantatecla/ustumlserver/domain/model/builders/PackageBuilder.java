package com.usantatecla.ustumlserver.domain.model.builders;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackageBuilder extends MemberBuilder {

    private BuilderContext context;
    protected String id;
    protected String name;
    protected List<Member> members;
    private ClassBuilder classBuilder;
    private InterfaceBuilder interfaceBuilder;
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
            case ON_INTERFACE:
                this.interfaceBuilder.id(id);
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
            case ON_INTERFACE:
                this.interfaceBuilder.name(name);
                break;
        }
        return this;
    }

    public PackageBuilder packages(Package... packages) {
        this.members.addAll(Arrays.asList(packages));
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

    public PackageBuilder _interface() {
        if (this.context == BuilderContext.ON_INTERFACE) {
            this.members.add(this.interfaceBuilder.build());
        } else this.context = BuilderContext.ON_INTERFACE;
        this.interfaceBuilder = new InterfaceBuilder();
        return this;
    }

    @Override
    public PackageBuilder use() {
        super.use();
        return this;
    }

    @Override
    public PackageBuilder target(Member member) {
        super.target(member);
        return this;
    }

    @Override
    public PackageBuilder role(String role) {
        super.role(role);
        return this;
    }

    public Package build() {
        if (this.packageBuilder != null) {
            this.members.add(this.packageBuilder.build());
        }
        if (this.classBuilder != null) {
            this.members.add(this.classBuilder.build());
        }
        if (this.interfaceBuilder != null) {
            this.members.add(this.interfaceBuilder.build());
        }
        Package pakage = new Package(this.name, this.members);
        pakage.setId(this.id);
        this.setRelations(pakage);
        return pakage;
    }

}
