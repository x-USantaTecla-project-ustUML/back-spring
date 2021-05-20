package com.usantatecla.ustumlserver.infrastructure.mongodb.entities;

import com.usantatecla.ustumlserver.domain.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SessionEntity {

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

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for (MemberEntity memberEntity: this.memberEntities){
            members.add(memberEntity.toMember());
        }
        return members;
    }

}
