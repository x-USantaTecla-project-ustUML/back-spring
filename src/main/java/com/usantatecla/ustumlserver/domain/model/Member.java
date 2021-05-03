package com.usantatecla.ustumlserver.domain.model;

public abstract class Member {

    protected String name;

    Member(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract void accept(Generator generator);

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Member other = (Member) obj;
        if (this.name == null) {
            return other.getName() == null;
        } else return this.name.equals(other.getName());
    }

}
