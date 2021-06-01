package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.*;
import lombok.AllArgsConstructor;
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

    public MemberEntity(String id, String name, List<Relation> relations) {
        this.id = id;
        this.name = name;
        this.relationEntities = new RelationCreator(relations).getRelationEntities();
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

    class RelationCreator implements RelationVisitor {

        private List<RelationEntity> relationEntities;

        RelationCreator(List<Relation> relations){
            this.relationEntities = new ArrayList<>();
            if(relations != null) {
                for (int i = 0; i < relations.size(); i++) {
                    relations.get(i).accept(this);
                }
            }
        }

        @Override
        public void visit(Use use) {
            this.relationEntities.add(new UseEntity(use, ));
        }

        List<RelationEntity> getRelationEntities(){
            return this.relationEntities;
        }

    }

}
