package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.RelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RelationEntityDeleter extends WithRelationDaosPersistence implements RelationVisitor {

    private MemberEntityFinder memberEntityFinder;
    private RelationEntity relationEntity;

    @Autowired
    RelationEntityDeleter(MemberEntityFinder memberEntityFinder) {
        this.memberEntityFinder = memberEntityFinder;
    }

    RelationEntity delete(Relation relation) {
        relation.accept(this);
        return this.relationEntity;
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
