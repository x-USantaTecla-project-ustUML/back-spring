package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class WithMembersMember extends Member {

    WithMembersMember(String name) {
        super(name);
    }

    public abstract Member find(String name);

    public abstract Member findRoute(String route);

    public void modify(String oldName, String newName) {
        Member member = this.find(oldName);
        if (member == null) {
            throw new ModelException(ErrorMessage.MEMBER_NOT_FOUND, oldName);
        }
        if (this.find(newName) != null) {
            throw new ModelException(ErrorMessage.MEMBER_ALREADY_EXISTS, newName);
        }
        member.setName(newName);
    }

    public Member deleteMember(String memberName) {
        Member member = this.find(memberName);
        if (member == null) {
            throw new ModelException(ErrorMessage.MEMBER_NOT_FOUND, memberName);
        }
        this.delete(member);
        return member;
    }

    abstract void delete(Member member);

}
