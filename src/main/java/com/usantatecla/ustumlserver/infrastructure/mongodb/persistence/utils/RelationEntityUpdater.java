package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils;

import com.usantatecla.ustumlserver.domain.model.relations.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class RelationEntityUpdater extends WithRelationDaosPersistence implements RelationVisitor {

    private RelationEntity relationEntity;

    public RelationEntity update(Relation relation) {
        relation.accept(this);
        return this.relationEntity;
    }

    @Override
    public void visit(Use use) {
        UseEntity useEntity = null;
        if (use.getId() == null) {
            useEntity = new UseEntity(use, this.memberEntityFinder.find(use.getTarget()));
        } else {
            Optional<UseEntity> optionalUseEntity = this.useDao.findById(use.getId());
            if (optionalUseEntity.isPresent()) {
                useEntity = optionalUseEntity.get();
                useEntity = new UseEntity(use, useEntity.getTarget());
            }
        }
        if (useEntity != null) {
            this.relationEntity = this.useDao.save(useEntity);
        } else {
            this.relationEntity = null;
        }
    }

    @Override
    public void visit(Composition composition) {
        CompositionEntity compositionEntity = null;
        if (composition.getId() == null) {
            compositionEntity = new CompositionEntity(composition, this.memberEntityFinder.find(composition.getTarget()));
        } else {
            Optional<CompositionEntity> optionalCompositionEntity = this.compositionDao.findById(composition.getId());
            if (optionalCompositionEntity.isPresent()) {
                compositionEntity = optionalCompositionEntity.get();
                compositionEntity = new CompositionEntity(composition, compositionEntity.getTarget());
            }
        }
        if (compositionEntity != null) {
            this.relationEntity = this.compositionDao.save(compositionEntity);
        } else {
            this.relationEntity = null;
        }
    }

    @Override
    public void visit(Aggregation aggregation) {
        AggregationEntity aggregationEntity = null;
        if (aggregation.getId() == null) {
            aggregationEntity = new AggregationEntity(aggregation, this.memberEntityFinder.find(aggregation.getTarget()));
        } else {
            Optional<AggregationEntity> optionalAggregationEntity = this.aggregationDao.findById(aggregation.getId());
            if (optionalAggregationEntity.isPresent()) {
                aggregationEntity = optionalAggregationEntity.get();
                aggregationEntity = new AggregationEntity(aggregation, aggregationEntity.getTarget());
            }
        }
        if (aggregationEntity != null) {
            this.relationEntity = this.aggregationDao.save(aggregationEntity);
        } else {
            this.relationEntity = null;
        }
    }

    @Override
    public void visit(Inheritance inheritance) {
        InheritanceEntity inheritanceEntity = null;
        if (inheritance.getId() == null) {
            inheritanceEntity = new InheritanceEntity(inheritance, this.memberEntityFinder.find(inheritance.getTarget()));
        } else {
            Optional<InheritanceEntity> optionalInheritanceEntity = this.inheritanceDao.findById(inheritance.getId());
            if (optionalInheritanceEntity.isPresent()) {
                inheritanceEntity = optionalInheritanceEntity.get();
                inheritanceEntity = new InheritanceEntity(inheritance, inheritanceEntity.getTarget());
            }
        }
        if (inheritanceEntity != null) {
            this.relationEntity = this.inheritanceDao.save(inheritanceEntity);
        } else {
            this.relationEntity = null;
        }
    }

    @Override
    public void visit(Association association) {
        AssociationEntity associationEntity = null;
        if (association.getId() == null) {
            associationEntity = new AssociationEntity(association, this.memberEntityFinder.find(association.getTarget()));
        } else {
            Optional<AssociationEntity> optionalAssociationEntity = this.associationDao.findById(association.getId());
            if (optionalAssociationEntity.isPresent()) {
                associationEntity = optionalAssociationEntity.get();
                associationEntity = new AssociationEntity(association, associationEntity.getTarget());
            }
        }
        if (associationEntity != null) {
            this.relationEntity = this.associationDao.save(associationEntity);
        } else {
            this.relationEntity = null;
        }
    }
}
