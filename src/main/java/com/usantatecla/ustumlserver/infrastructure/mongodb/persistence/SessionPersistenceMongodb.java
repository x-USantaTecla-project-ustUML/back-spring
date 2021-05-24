package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.SessionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SessionPersistenceMongodb implements SessionPersistence {

    private SessionDao sessionDao;
    private PackageDao packageDao;
    private ClassDao classDao;
    private MemberEntity memberEntity;

    @Autowired
    public SessionPersistenceMongodb(SessionDao sessionDao, PackageDao packageDao, ClassDao classDao) {
        this.sessionDao = sessionDao;
        this.packageDao = packageDao;
        this.classDao = classDao;
    }

    @Override
    public List<Member> read(String sessionId) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            sessionEntity = new SessionEntity(sessionId,
                    Collections.singletonList(this.packageDao.findByName("name")));
            this.sessionDao.save(sessionEntity);
        }
        return sessionEntity.getMembers();
    }

    @Override
    public void update(String sessionId, List<Member> members) {
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : members) {
            member.accept(this);
            memberEntities.add(this.memberEntity);
        }
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
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

    @Override
    public void visit(Package pakage) {
        this.memberEntity = this.packageDao.findByName(pakage.getName());
    }

    @Override
    public void visit(Class clazz) {
        this.memberEntity = this.classDao.findByName(clazz.getName());
    }

}
