package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
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
    private List<MemberEntity> memberEntities;



    public Package toPackage() {
        Package pakage = new Package();
        BeanUtils.copyProperties(this, pakage);
        List<Member> members = new ArrayList<>();
        if (Objects.nonNull(this.getMemberEntities())) {
            for (MemberEntity memberEntity: this.getMemberEntities()){
                members.add(memberEntity.toMember());
            }
        }
        pakage.setMembers(members);
        return pakage;
    }

    @Override
    protected Member toMember() {
        return this.toPackage();
    }

    public void add(MemberEntity memberEntity) {
        if (this.memberEntities == null) {
            this.memberEntities = new ArrayList<>();
        }
        this.memberEntities.add(memberEntity);
    }

}
