package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RelationEntityDeleter extends WithRelationDaosPersistence implements RelationVisitor {

    public void delete(Relation relation) {
        relation.accept(this);
    }

    void deleteAll(Member member) {
        MemberEntity memberEntity = this.memberEntityFinder.find(member);
        this.useDao.deleteByTarget(memberEntity);
        this.compositionDao.deleteByTarget(memberEntity);
        this.inheritanceDao.deleteByTarget(memberEntity);
        this.aggregationDao.deleteByTarget(memberEntity);
        this.associationDao.deleteByTarget(memberEntity);
    }

    @Override
    public void visit(Use use) {
        this.useDao.deleteById(use.getId());
    }

    @Override
    public void visit(Composition composition) {
        this.compositionDao.deleteById(composition.getId());
    }

    @Override
    public void visit(Inheritance inheritance) {
        this.inheritanceDao.deleteById(inheritance.getId());
    }

    @Override
    public void visit(Aggregation aggregation) {
        this.aggregationDao.deleteById(aggregation.getId());
    }

    @Override
    public void visit(Association association) {
        this.associationDao.deleteById(association.getId());
    }
}
