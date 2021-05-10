package com.usantatecla.ustumlserver.domain.model;

import java.util.ArrayList;
import java.util.List;

public class PackageBuilder {

    private String name;
    private List<Member> members;

    public PackageBuilder() {
        this.name = "name";
        this.members = new ArrayList<>();
    }

    public PackageBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PackageBuilder clazz() {
        this.members.add(new ClassBuilder().build());
        return this;
    }

    public Package build(){
        return new Package(this.name, this.members);
    }

}
