package com.usantatecla.ustumlserver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Package extends Member {

    private List<Member> members;

    public Package(String name, List<Member> members) {
        super(name, new ArrayList<>());
        this.members = members;
    }

    public void add(Member member) {
        this.members.add(member);
    }

    public boolean find(String name) {
        for(Member member: this.members) {
            if (member.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String accept(Generator generator) {
        return generator.visit(this);
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {
        memberVisitor.visit(this);
    }

}
