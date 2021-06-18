package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberPersistenceMongodb {

    @Autowired
    protected RelationEntityDeleter relationEntityDeleter;

    protected void deleteRelations(List<Relation> relations) {
        for (Relation relation : relations) {
            this.relationEntityDeleter.delete(relation);
        }
    }

}
