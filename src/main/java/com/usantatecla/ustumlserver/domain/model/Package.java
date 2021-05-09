package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Package extends Member {

    private List<Member> members;

    public Package(String name, List<Member> members) {
        super(name);
        this.members = members;
    }

    public void add(Member member) {
        this.members.add(member);
    }

    @Override
    public String accept(Generator generator) {
        return generator.visit(this);
    }

}
