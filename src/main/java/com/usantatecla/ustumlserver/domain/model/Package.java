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

    public Package(String id, String name, List<Member> members) {
        super(id, name);
        this.members = members;
    }

    public void add(Member member) {
        this.members.add(member);
    }

    public Member find(String id) {
        for(Member member: this.members) {
            if (member.getId().equals(id)){
                return member;
            }
        }
        return null;
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
