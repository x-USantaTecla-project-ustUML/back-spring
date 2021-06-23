package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;

import java.util.List;

interface MemberPersistence {
    Member deleteRelations(Member member, List<Relation> relations);
}
