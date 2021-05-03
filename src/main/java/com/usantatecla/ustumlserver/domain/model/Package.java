package com.usantatecla.ustumlserver.domain.model;

import java.util.List;
import java.util.Objects;

public class Package extends Member {

    private List<Member> members;

    public Package(String name, List<Member> members) {
        super(name);
        this.members = members;
    }

    @Override
    public void accept(Generator generator) {
        generator.visit(this);
    }

    public List<Member> getMembers() {
        return this.members;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Package other = (Package) obj;
        if (this.members == null) {
            return other.getMembers() == null;
        } else return this.members.equals(other.getMembers());
    }

}
