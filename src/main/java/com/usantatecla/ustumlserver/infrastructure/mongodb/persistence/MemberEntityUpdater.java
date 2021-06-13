package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberEntityUpdater extends WithMemberDaosPersistence implements MemberVisitor {

    private MemberEntityFinder memberEntityFinder;
    private RelationEntityUpdater relationEntityUpdater;
    private MemberEntity memberEntity;

    @Autowired
    public MemberEntityUpdater(MemberEntityFinder memberEntityFinder, RelationEntityUpdater relationEntityUpdater) {
        this.memberEntityFinder = memberEntityFinder;
        this.relationEntityUpdater = relationEntityUpdater;
    }

    MemberEntity update(Member member) {
        member.accept(this);
        return this.memberEntity;
    }

    @Override
    public void visit(Account account) {
        AccountEntity accountEntity;
        if (account.getId() == null) {
            accountEntity = new AccountEntity(account);
        } else {
            accountEntity = (AccountEntity) this.memberEntityFinder.find(account);
        }
        List<ProjectEntity> projectEntities = new ArrayList<>();
        for (Project project : account.getProjects()) {
            project.accept(this);
            projectEntities.add((ProjectEntity) this.memberEntity);
        }
        accountEntity.setProjectEntities(projectEntities);
        this.memberEntity = this.accountDao.save(accountEntity);
    }

    @Override
    public void visit(Package pakage) {
        PackageEntity packageEntity;
        if (pakage.getId() == null) {
            packageEntity = new PackageEntity(pakage);
        } else {
            packageEntity = (PackageEntity) this.memberEntityFinder.find(pakage);
        }
        this.updatePackageMembers(packageEntity, pakage.getMembers());
        this.updateRelations(packageEntity, pakage.getRelations());
        this.memberEntity = this.packageDao.save(packageEntity);
    }

    @Override
    public void visit(Project project) {
        ProjectEntity projectEntity;
        if (project.getId() == null) {
            projectEntity = new ProjectEntity(project);
        } else {
            projectEntity = (ProjectEntity) this.memberEntityFinder.find(project);
        }
        this.updatePackageMembers(projectEntity, project.getMembers());
        this.updateRelations(projectEntity, project.getRelations());
        this.memberEntity = this.projectDao.save(projectEntity);
    }

    private void updatePackageMembers(PackageEntity packageEntity, List<Member> members) {
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : members) {
            member.accept(this);
            memberEntities.add(this.memberEntity);
        }
        packageEntity.setMemberEntities(memberEntities);
    }

    @Override
    public void visit(Class clazz) {
        ClassEntity classEntity;
        if (clazz.getId() == null) {
            classEntity = new ClassEntity(clazz);
        } else {
            classEntity = (ClassEntity) this.memberEntityFinder.find(clazz);
        }
        this.updateRelations(classEntity, clazz.getRelations());
        this.memberEntity = this.classDao.save(classEntity);
    }

    @Override
    public void visit(Interface _interface) {
        InterfaceEntity interfaceEntity;
        if (_interface.getId() == null) {
            interfaceEntity = new InterfaceEntity(_interface);
        } else {
            interfaceEntity = (InterfaceEntity) this.memberEntityFinder.find(_interface);
        }
        this.updateRelations(interfaceEntity, _interface.getRelations());
        this.memberEntity = this.interfaceDao.save(interfaceEntity);
    }

    @Override
    public void visit(Enum _enum) {
        EnumEntity enumEntity;
        if (_enum.getId() == null) {
            enumEntity = new EnumEntity(_enum);
        } else {
            enumEntity = (EnumEntity) this.memberEntityFinder.find(_enum);
        }
        this.updateRelations(enumEntity, _enum.getRelations());
        this.memberEntity = this.enumDao.save(enumEntity);
    }

    private void updateRelations(MemberEntity memberEntity, List<Relation> relations) {
        List<RelationEntity> relationEntities = new ArrayList<>();
        for (Relation relation : relations) {
            relationEntities.add(this.relationEntityUpdater.update(relation));
        }
        memberEntity.setRelationEntities(relationEntities);
    }

}
