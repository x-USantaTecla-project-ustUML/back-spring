package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@SuperBuilder
@Document
public abstract class MemberEntity {
    @Id
    private String id;
    private String name;
    @DBRef(lazy = true)
    private List<RelationEntity> relationEntities;

    public MemberEntity(String id, String name) {
        this.id = id;
        this.name = name;
        this.relationEntities = new ArrayList<>();
    }

    List<Relation> getRelations() {
        List<Relation> relations = new ArrayList<>();
        if (Objects.nonNull(this.getRelationEntities())) {
            for (RelationEntity relationEntity : this.getRelationEntities()) {
                relations.add(relationEntity.toRelation());
            }
        }
        return relations;
    }

    protected abstract Member toMember();

    protected abstract Member toMemberWithoutRelations();

}
