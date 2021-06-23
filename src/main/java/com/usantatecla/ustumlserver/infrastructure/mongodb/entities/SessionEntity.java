package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SessionEntity {

    private static final int PROJECT_INDEX = 1;
    @Id
    private String id;
    @Indexed(unique = true)
    private String sessionId;
    @DBRef(lazy = true)
    private List<MemberEntity> memberEntities;

    public SessionEntity(String sessionId, List<MemberEntity> memberEntities) {
        this.sessionId = sessionId;
        this.memberEntities = memberEntities;
    }

    public void add(MemberEntity memberEntity) {
        this.memberEntities.add(memberEntity);
    }

    public void delete(MemberEntity memberEntity) {
        if (!this.memberEntities.remove(memberEntity)) {
            throw new PersistenceException(ErrorMessage.SESSION_MEMBER_NOT_FOUND, memberEntity.getName());
        }
    }

    public List<Member> getMembersToStack() {
        List<MemberEntity> memberEntities;
        if (this.memberEntities.size() > 3) {
            memberEntities = List.of(this.memberEntities.get(0), this.memberEntities.get(this.memberEntities.size() - 2),
                 this.memberEntities.get(this.memberEntities.size() - 1));
        } else {
            memberEntities = this.memberEntities;
        }
        return memberEntities.stream()
                .map(MemberEntity::toMember)
                .collect(Collectors.toList());
    }

}
