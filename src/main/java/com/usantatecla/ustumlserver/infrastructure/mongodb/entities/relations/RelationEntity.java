package com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations;

import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public abstract class RelationEntity {

    @Id
    private String id;
    @DBRef(lazy = true)
    private MemberEntity target;
    private String role;
    private String targetRoute;

    public abstract Relation toRelation();

}
