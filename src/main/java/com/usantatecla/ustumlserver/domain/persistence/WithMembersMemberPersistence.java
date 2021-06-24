package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.WithMembersMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithMembersMemberPersistence {
    WithMembersMember deleteMembers(WithMembersMember withMembersMember, List<Member> members);
}
