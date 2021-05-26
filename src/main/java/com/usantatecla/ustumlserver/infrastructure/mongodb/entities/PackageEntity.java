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
public class PackageEntity extends MemberEntity {

    @DBRef(lazy = true)
    protected List<MemberEntity> memberEntities;

    public PackageEntity(Package pakage) {
        super(pakage.getId(), pakage.getName());
        this.memberEntities = new ArrayList<>();
    }

    public Package toPackage() {
        Package pakage = new Package();
        BeanUtils.copyProperties(this, pakage);
        pakage.setMembers(this.getMembers());
        return pakage;
    }

    public Project toProject() {
        Project project = new Project();
        BeanUtils.copyProperties(this, project);
        project.setMembers(this.getMembers());
        return project;
    }

    private List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        if (Objects.nonNull(this.getMemberEntities())) {
            for (MemberEntity memberEntity : this.getMemberEntities()) {
                members.add(memberEntity.toMember());
            }
        }
        return members;
    }

    public void add(MemberEntity memberEntity) {
        if (this.memberEntities == null) {
            this.memberEntities = new ArrayList<>();
        }
        this.memberEntities.add(memberEntity);
    }

    @Override
    protected Member toMember() {
        return this.toPackage();
    }

}
