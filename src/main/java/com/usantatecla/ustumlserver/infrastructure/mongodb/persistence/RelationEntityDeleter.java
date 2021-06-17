package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.RelationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RelationEntityDeleter extends WithRelationDaosPersistence implements RelationVisitor {

    private RelationEntity relationEntity;

    RelationEntity delete(Relation relation) {
        relation.accept(this);
        return this.relationEntity;
    }

    void deleteAll(Member member) {
        MemberEntity memberEntity = this.memberEntityFinder.find(member);
        this.useDao.deleteAll(this.useDao.findByTarget(memberEntity));
        this.compositionDao.deleteAll(this.compositionDao.findByTarget(memberEntity));
        this.inheritanceDao.deleteAll(this.inheritanceDao.findByTarget(memberEntity));
        this.aggregationDao.deleteAll(this.aggregationDao.findByTarget(memberEntity));
        this.associationDao.deleteAll(this.associationDao.findByTarget(memberEntity));
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
