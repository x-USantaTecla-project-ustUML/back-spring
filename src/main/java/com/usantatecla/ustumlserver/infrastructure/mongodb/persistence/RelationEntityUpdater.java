package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.domain.model.RelationVisitor;
import com.usantatecla.ustumlserver.domain.model.Use;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UseDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.RelationEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.UseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class RelationEntityUpdater implements RelationVisitor {

    private UseDao useDao;
    private MemberEntityFinder memberEntityFinder;
    private RelationEntity relationEntity;

    @Autowired
    RelationEntityUpdater(UseDao useDao, MemberEntityFinder memberEntityFinder) {
        this.useDao = useDao;
        this.memberEntityFinder = memberEntityFinder;
    }

    RelationEntity update(Relation relation) {
        relation.accept(this);
        return this.relationEntity;
    }

    @Override
    public void visit(Use use) {
        UseEntity useEntity;
        if (use.getId() == null) {
            useEntity = new UseEntity(use, this.memberEntityFinder.find(use.getTarget()));
            useEntity = this.useDao.save(useEntity);
        } else {
            Optional<UseEntity> optionalUseEntity = this.useDao.findById(use.getId());
            if (optionalUseEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.RELATION_NOT_FOUND);
            } else {
                useEntity = optionalUseEntity.get();
            }
        }
        this.relationEntity = useEntity;
    }

}
