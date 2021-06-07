package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AggregationDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.CompositionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UseDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AggregationEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.CompositionEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.RelationEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class RelationEntityUpdater implements RelationVisitor {

    private UseDao useDao;
    private CompositionDao compositionDao;
    private AggregationDao aggregationDao;
    private MemberEntityFinder memberEntityFinder;
    private RelationEntity relationEntity;

    @Autowired
    RelationEntityUpdater(UseDao useDao, CompositionDao compositionDao, AggregationDao aggregationDao,
                          MemberEntityFinder memberEntityFinder) {
        this.useDao = useDao;
        this.compositionDao = compositionDao;
        this.aggregationDao = aggregationDao;
        this.memberEntityFinder = memberEntityFinder;
    }

    RelationEntity update(Relation relation) {
        relation.accept(this);
        return this.relationEntity;
    }

    @Override
    public void visit(Use use) { // TODO generalizar si se puede
        UseEntity useEntity;
        if (use.getId() == null) {
            useEntity = new UseEntity(use, this.memberEntityFinder.find(use.getTarget()));
        } else {
            Optional<UseEntity> optionalUseEntity = this.useDao.findById(use.getId());
            if (optionalUseEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.RELATION_NOT_FOUND);
            } else {
                useEntity = optionalUseEntity.get();
            }
        }
        this.relationEntity = this.useDao.save(useEntity);
    }

    @Override
    public void visit(Composition composition) {
        CompositionEntity compositionEntity;
        if (composition.getId() == null) {
            compositionEntity = new CompositionEntity(composition, this.memberEntityFinder.find(composition.getTarget()));
        } else {
            Optional<CompositionEntity> optionalCompositionEntity = this.compositionDao.findById(composition.getId());
            if (optionalCompositionEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.RELATION_NOT_FOUND);
            } else {
                compositionEntity = optionalCompositionEntity.get();
            }
        }
        this.relationEntity = this.compositionDao.save(compositionEntity);
    }

    @Override
    public void visit(Aggregation aggregation) {
        AggregationEntity aggregationEntity;
        if (aggregation.getId() == null) {
            aggregationEntity = new AggregationEntity(aggregation, this.memberEntityFinder.find(aggregation.getTarget()));
        } else {
            Optional<AggregationEntity> optionalAggregationEntity = this.aggregationDao.findById(aggregation.getId());
            if (optionalAggregationEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.RELATION_NOT_FOUND);
            } else {
                aggregationEntity = optionalAggregationEntity.get();
            }
        }
        this.relationEntity = this.aggregationDao.save(aggregationEntity);
    }
}
