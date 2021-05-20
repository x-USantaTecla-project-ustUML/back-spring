package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.persistence.MemberPersistence;
import com.usantatecla.ustumlserver.domain.services.CommandParserException;
import com.usantatecla.ustumlserver.domain.services.Error;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.MemberDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberPersistenceMongodb implements MemberPersistence {

    private MemberDao memberDao;

    @Autowired
    public MemberPersistenceMongodb(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public Member read(String name) {
        return this.find(name).toMember();
    }

    private MemberEntity find(String name) {
        MemberEntity memberEntity = this.memberDao.findByName(name);
        if (memberEntity == null) {
            throw new CommandParserException(Error.MEMBER_NOT_FOUND, name);
        }
        return memberEntity;
    }

}
