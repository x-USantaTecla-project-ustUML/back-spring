package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class MemberEntityFinder extends WithDaosPersistence implements MemberVisitor {

    private MemberEntity memberEntity;

    MemberEntityFinder(AccountDao accountDao, PackageDao packageDao, ProjectDao projectDao, ClassDao classDao, InterfaceDao interfaceDao) {
        super(accountDao, packageDao, projectDao, classDao, interfaceDao);
    }

    MemberEntity find(Member member) {
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

}
