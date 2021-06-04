package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.SessionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SessionPersistenceMongodb extends WithDaosPersistence implements SessionPersistence {

    private SessionDao sessionDao;

    @Autowired
    public SessionPersistenceMongodb(SessionDao sessionDao, PackageDao packageDao, ClassDao classDao, AccountDao accountDao) {
        super(accountDao, packageDao, classDao);
        this.sessionDao = sessionDao;
    }

    @Override
    public List<Member> read(String sessionId, String email) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            sessionEntity = new SessionEntity(sessionId, Collections.singletonList(this.accountDao.findByEmail(email)));
            this.sessionDao.save(sessionEntity);
        }
        return sessionEntity.getMembers();
    }

    @Override
    public void update(String sessionId, List<Member> members) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            throw new PersistenceException(ErrorMessage.SESSION_NOT_FOUND, sessionId);
        }
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : members) {
            memberEntities.add(new MemberEntityFinder(this.accountDao, this.packageDao, this.classDao).find(member));
        }
        sessionEntity.setMemberEntities(memberEntities);
        this.sessionDao.save(sessionEntity);
    }

    @Override
    public void delete(String sessionId) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity != null) {
            this.sessionDao.delete(sessionEntity);
        }
    }

}
