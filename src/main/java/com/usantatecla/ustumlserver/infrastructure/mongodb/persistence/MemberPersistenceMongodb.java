package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.domain.persistence.MemberPersistence;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityDeleter;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityUpdater;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.RelationEntityDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberPersistenceMongodb implements MemberPersistence {

    @Autowired
    protected RelationEntityDeleter relationEntityDeleter;
    @Autowired
    protected MemberEntityUpdater memberEntityUpdater;

    @Override
    public Member deleteRelations(Member member, List<Relation> relations) {
        for (Relation relation : relations) {
            this.relationEntityDeleter.delete(relation);
        }
        return this.update(member);
    }

    @Override
    public Member update(Member member) {
        return this.memberEntityUpdater.update(member).toMember();
    }
}
