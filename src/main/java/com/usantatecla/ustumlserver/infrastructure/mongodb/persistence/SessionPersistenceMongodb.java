package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.domain.services.Error;
import com.usantatecla.ustumlserver.domain.services.parsers.CommandParserException;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.SessionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                    Collections.singletonList(this.packageDao.findByName("name"))); // TODO
            this.sessionDao.save(sessionEntity);
        }
        return sessionEntity.getMembers();
    }

    @Override
    public Error update(String sessionId, List<Member> members) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            return Error.SESSION_NOT_FOUND;
        }
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : members) {
            member.accept(this);
            memberEntities.add(this.memberEntity);
        }
        sessionEntity.setMemberEntities(memberEntities);
        this.sessionDao.save(sessionEntity);
        return Error.NULL;
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
        Optional<PackageEntity> packageEntity = this.packageDao.findById(pakage.getId());
        if(packageEntity.isEmpty()) {
            throw new CommandParserException(Error.MEMBER_NOT_FOUND);
        }
        this.memberEntity = packageEntity.get();
    }

    @Override
    public void visit(Class clazz) {
        Optional<ClassEntity> classEntity = this.classDao.findById(clazz.getId());
        if(classEntity.isEmpty()) {
            throw new CommandParserException(Error.MEMBER_NOT_FOUND);
        }
        this.memberEntity = classEntity.get();
    }

}
