package com.usantatecla.ustumlserver.domain.model.relations;

import com.usantatecla.ustumlserver.domain.model.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public abstract class Relation {

    protected String id;
    private Member target;
    private String role;
    private String targetRoute;

    public Relation(Member target, String role) {
        this.target = target;
        if (role != null) {
            this.role = role;
        } else {
            this.role = "";
        }
    }

    public abstract void accept(RelationVisitor relationVisitor);

    public abstract String getUstName();

    public abstract String getPlantUml();

    public abstract Relation copy(Member target, String role);

    public String getTargetId() {
        return this.target.getId();
    }

    public String getTargetName() {
        return this.target.getName();
    }

    public String getTargetPlantUML() {
        return this.target.getPlantUml();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relation)) return false;

        Relation relation = (Relation) o;

        if (!this.getTarget().getName().equals(relation.getTarget().getName())) return false;
        if (this.getRole() != null ? !this.getRole().equals(relation.getRole()) : relation.getRole() != null) return false;
        return this.getTargetRoute() != null ? this.getTargetRoute().equals(relation.getTargetRoute()) : relation.getTargetRoute() == null;
    }

    @Override
    public int hashCode() {
        int result = getTarget().hashCode();
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (getTargetRoute() != null ? getTargetRoute().hashCode() : 0);
        return result;
    }
}
