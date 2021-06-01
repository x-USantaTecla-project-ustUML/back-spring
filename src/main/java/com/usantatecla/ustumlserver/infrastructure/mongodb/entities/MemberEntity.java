package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public abstract class MemberEntity {
    @Id
    private String id;
    private String name;
    @DBRef(lazy = true)
    private List<RelationEntity> relationEntities;

    List<RelationEntity> toRelationEntity(List<Relation> relations) {
        for (Relation relation: relations) {

        }
        return null;
    }

    protected abstract Member toMember();

}
