package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.domain.services.Error;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.MemberDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.SessionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionPersistenceMongodb implements SessionPersistence {

    private SessionDao sessionDao;
    private MemberDao memberDao;

    @Autowired
    public SessionPersistenceMongodb(SessionDao sessionDao, MemberDao memberDao) {
        this.sessionDao = sessionDao;
        this.memberDao = memberDao;
    }

    @Override
    public List<Member> read(String sessionId) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if(sessionEntity == null){
            sessionEntity = new SessionEntity(sessionId,
                    Collections.singletonList(this.memberDao.findByName("name")));
            this.sessionDao.save(sessionEntity);
        }
        return sessionEntity.getMembers();
    }

    @Override
    public void update(String sessionId, List<Member> members) {
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member: members) {
            memberEntities.add(this.memberDao.findByName(member.getName()));
        }
        this.sessionDao.save(new SessionEntity(sessionId, memberEntities));
    }

}
