package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.persistence.SessionPersistence;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.SessionDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.SessionEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@Repository
public class SessionPersistenceMongodb implements SessionPersistence {

    private SessionDao sessionDao;
    private PackageDao packageDao;
    private ClassDao classDao;

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
    public void update(String sessionId, List<Member> members) {
        SessionEntity sessionEntity = this.sessionDao.findBySessionId(sessionId);
        if (sessionEntity == null) {
            throw new PersistenceException(ErrorMessage.SESSION_NOT_FOUND, sessionId);
        }
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : members) {
            memberEntities.add(new MemberEntityFinder(this).find(member));
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

    class MemberEntityFinder implements MemberVisitor {

        private SessionPersistenceMongodb sessionPersistence;
        private MemberEntity memberEntity;

        MemberEntityFinder(SessionPersistenceMongodb sessionPersistence) {
            this.sessionPersistence = sessionPersistence;
        }

        MemberEntity find(Member member) {
            member.accept(this);
            return this.memberEntity;
        }

        @Override
        public void visit(Package pakage) {
            Optional<PackageEntity> packageEntity = this.sessionPersistence.getPackageDao().findById(pakage.getId());
            if (packageEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, pakage.getName());
            }
            this.memberEntity = packageEntity.get();
        }

        @Override
        public void visit(Class clazz) {
            Optional<ClassEntity> classEntity = this.sessionPersistence.getClassDao().findById(clazz.getId());
            if (classEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, clazz.getName());
            }
            this.memberEntity = classEntity.get();
        }

    }

}
