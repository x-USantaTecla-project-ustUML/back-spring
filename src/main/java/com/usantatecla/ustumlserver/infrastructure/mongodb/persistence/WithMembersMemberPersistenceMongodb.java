package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.WithMembersMember;
import com.usantatecla.ustumlserver.domain.persistence.WithMembersMemberPersistence;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityDeleter;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WithMembersMemberPersistenceMongodb implements WithMembersMemberPersistence {

    private MemberEntityDeleter memberEntityDeleter;
    private MemberEntityUpdater memberEntityUpdater;

    @Autowired
    public WithMembersMemberPersistenceMongodb(MemberEntityDeleter memberEntityDeleter, MemberEntityUpdater memberEntityUpdater) {
        this.memberEntityDeleter = memberEntityDeleter;
        this.memberEntityUpdater = memberEntityUpdater;
    }

    @Override
    public WithMembersMember deleteMembers(WithMembersMember withMembersMember, List<Member> members) {
        for (Member member : members) {
            this.memberEntityDeleter.delete(member);
        }
        return (WithMembersMember) this.memberEntityUpdater.update(withMembersMember).toMember();
    }

}
