package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Document
public class ProjectEntity extends MemberEntity{

    @DBRef(lazy = true)
    private List<MemberEntity> memberEntities;

    public ProjectEntity(Project project) {
        super(project.getId(), project.getName());
        this.memberEntities = new ArrayList<>();
    }

    public Project toProject() {
        Project project = new Project();
        BeanUtils.copyProperties(this, project);
        List<Member> members = new ArrayList<>();
        if (Objects.nonNull(this.getMemberEntities())) {
            for (MemberEntity memberEntity : this.getMemberEntities()) {
                members.add(memberEntity.toMember());
            }
        }
        project.setMembers(members);
        return project;
    }

    public void add(MemberEntity memberEntity) {
        if (this.memberEntities == null) {
            this.memberEntities = new ArrayList<>();
        }
        this.memberEntities.add(memberEntity);
    }

    @Override
    protected Member toMember() {
        return this.toProject();
    }

}