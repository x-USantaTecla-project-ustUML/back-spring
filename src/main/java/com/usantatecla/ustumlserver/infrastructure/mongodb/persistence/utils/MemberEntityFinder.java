package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils;

import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Interface;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.AccountEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ProjectEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.EnumEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.classDiagram.InterfaceEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberEntityFinder extends WithMemberDaosPersistence implements MemberVisitor {

    private MemberEntity memberEntity;

    public MemberEntity find(Member member) {
        if (member.getId() == null) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, member.getName());
        }
        member.accept(this);
        return this.memberEntity;
    }

    @Override
    public void visit(Account account) {
        Optional<AccountEntity> accountEntity = this.accountDao.findById(account.getId());
        if (accountEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, account.getName());
        }
        this.memberEntity = accountEntity.get();
    }

    @Override
    public void visit(Package pakage) {
        Optional<PackageEntity> packageEntity = this.packageDao.findById(pakage.getId());
        if (packageEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, pakage.getName());
        }
        this.memberEntity = packageEntity.get();
    }

    @Override
    public void visit(Project project) {
        Optional<ProjectEntity> projectEntity = this.projectDao.findById(project.getId());
        if (projectEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, project.getName());
        }
        this.memberEntity = projectEntity.get();
    }

    @Override
    public void visit(Class clazz) {
        Optional<ClassEntity> classEntity = this.classDao.findById(clazz.getId());
        if (classEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, clazz.getName());
        }
        this.memberEntity = classEntity.get();
    }

    @Override
    public void visit(Interface _interface) {
        Optional<InterfaceEntity> interfaceEntity = this.interfaceDao.findById(_interface.getId());
        if (interfaceEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, _interface.getName());
        }
        this.memberEntity = interfaceEntity.get();
    }

    @Override
    public void visit(Enum _enum) {
        Optional<EnumEntity> enumEntity = this.enumDao.findById(_enum.getId());
        if (enumEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, _enum.getName());
        }
        this.memberEntity = enumEntity.get();
    }

}
