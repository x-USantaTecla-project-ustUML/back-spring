package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberPersistence {
    Member deleteRelations(Member member, List<Relation> relations);
    Member update(Member member);
}
