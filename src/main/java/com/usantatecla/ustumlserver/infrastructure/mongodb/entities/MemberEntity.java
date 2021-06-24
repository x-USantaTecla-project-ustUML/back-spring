package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.relations.Relation;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.relations.RelationEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@SuperBuilder
@Document
public abstract class MemberEntity {
    @Id
    protected String id;
    protected String name;
    @DBRef(lazy = true)
    protected List<RelationEntity> relationEntities;

    public MemberEntity(String id, String name) {
        this.id = id;
        this.name = name;
        this.relationEntities = new ArrayList<>();
    }

    protected List<Relation> getRelations() {
        List<Relation> relations = new ArrayList<>();
        if (Objects.nonNull(this.getRelationEntities())) {
            this.relationEntities = this.relationEntities.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            for (RelationEntity relationEntity : this.getRelationEntities()) {
                relations.add(relationEntity.toRelation());
            }
        }
        return relations;
    }

    public abstract Member toMember();

    public abstract Member toMemberWithoutRelations();

}
