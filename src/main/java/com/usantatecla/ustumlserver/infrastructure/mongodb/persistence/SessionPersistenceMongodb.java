package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.SessionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils.MemberEntityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class SessionPersistenceMongodb implements SessionPersistence {

    private MemberEntityFinder memberEntityFinder;
    private AccountDao accountDao;
    private SessionDao sessionDao;

    @Autowired
    public SessionPersistenceMongodb(MemberEntityFinder memberEntityFinder, SessionDao sessionDao, AccountDao accountDao) {
        this.memberEntityFinder = memberEntityFinder;
        this.accountDao = accountDao;
        this.sessionDao = sessionDao;
    }

    @Override
    public List<Member> read(String sessionId, String email) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            sessionEntity = new SessionEntity(sessionId, Collections.singletonList(this.accountDao.findByEmail(email)));
            this.sessionDao.save(sessionEntity);
        }

        return sessionEntity.getMembersToStack();
    }

    @Override
    public void add(String sessionId, Member member) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            throw new PersistenceException(ErrorMessage.SESSION_NOT_FOUND, sessionId);
        }
        sessionEntity.add(this.memberEntityFinder.find(member));
        this.sessionDao.save(sessionEntity);
    }

    @Override
    public void delete(String sessionId) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity != null) {
            this.sessionDao.delete(sessionEntity);
        }
    }

    @Override
    public void delete(String sessionId, Member member) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            throw new PersistenceException(ErrorMessage.SESSION_NOT_FOUND, sessionId);
        }
        sessionEntity.delete(this.memberEntityFinder.find(member));
        this.sessionDao.save(sessionEntity);
    }

}
