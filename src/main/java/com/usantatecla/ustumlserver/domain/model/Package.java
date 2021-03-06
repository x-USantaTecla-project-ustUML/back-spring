package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.domain.model.generators.Generator;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Package extends WithMembersMember {

    private static final String UML_NAME = "package";

    protected List<Member> members;

    public Package(String name, List<Member> members) {
        super(name);
        this.members = members;
    }

    public void add(Member member) {
        if (this.find(member.getName()) != null) {
            throw new ModelException(ErrorMessage.MEMBER_ALREADY_EXISTS, member.getName());
        }
        this.members.add(member);
    }

    public Member find(String name) {
        for (Member member : this.members) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public Member findRoute(String route) {
        return this.findRoute(this.getStackRoute(route));
    }

    @Override
    void delete(Member member) {
        this.members.remove(member);
    }


    public Member findRoute(Deque<String> route) {
        Member member = this.find(route.pop());
        if (!route.isEmpty()) {
            if (member != null && member.isPackage()) {
                return ((Package) member).findRoute(route);
            } else {
                return null;
            }
        }
        return member;
    }

    public List<Package> getPackageMembers() {
        List<Package> packageMembers = new ArrayList<>();
        for (Member member : this.members) {
            if (member.isPackage()) {
                packageMembers.add((Package) member);
            }
        }
        return packageMembers;
    }

    @Override
    public String accept(Generator generator) {
        return generator.visit(this);
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {
        memberVisitor.visit(this);
    }

    @Override
    public String getUstName() {
        return Package.UML_NAME + ":";
    }

    @Override
    public String getPlantUml() {
        return Package.UML_NAME;
    }

    @Override
    public boolean isPackage() {
        return true;
    }

}
