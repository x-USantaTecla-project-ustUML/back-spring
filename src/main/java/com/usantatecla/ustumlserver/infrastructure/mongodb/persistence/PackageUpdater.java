package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Repository
public class PackageUpdater implements MemberVisitor, RelationVisitor {

    private AccountDao accountDao;
    private PackageDao packageDao;
    private ClassDao classDao;
    private UseDao useDao;
    private MemberDao memberDao;
    private Stack<MemberEntity> memberEntities;
    private List<RelationEntity> relationEntities;

    @Autowired
    public PackageUpdater(AccountDao accountDao, PackageDao packageDao, ClassDao classDao, UseDao useDao, MemberDao memberDao) {
        this.accountDao = accountDao;
        this.packageDao = packageDao;
        this.classDao = classDao;
        this.useDao = useDao;
        this.memberDao = memberDao;
        this.memberEntities = new Stack<>();
        this.relationEntities = new ArrayList<>();
    }

    PackageEntity update(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        if (pakage.getId() != null) {
            packageEntity = this.find(pakage.getId());
        }
        packageEntity.setMemberEntities(this.updateMembersList(pakage.getMembers()));
        packageEntity.setRelationEntities(this.updateRelationsList(pakage.getRelations()));
        return this.packageDao.save(packageEntity);
    }

    private List<MemberEntity> updateMembersList(List<Member> members) {
        for (Member member : members) {
            member.accept(this);
        }
        List<MemberEntity> memberEntities = new ArrayList<>(this.memberEntities);
        this.memberEntities.clear();
        return memberEntities;
    }

    List<RelationEntity> updateRelationsList(List<Relation> relations) {
        for (Relation relation : relations) {
            relation.accept(this);
        }
        List<RelationEntity> relationEntities = new ArrayList<>(this.relationEntities);
        this.relationEntities.clear();
        return relationEntities;
    }

    PackageEntity find(String id) {
        Optional<PackageEntity> packageEntity = this.packageDao.findById(id);
        if (packageEntity.isEmpty()) {
            throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, id);
        }
        return packageEntity.get();
    }

    @Override
    public void visit(Account account) {

    }

    @Override
    public void visit(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        if (pakage.getId() != null) {
            packageEntity = this.find(pakage.getId());
        }
        this.memberEntities.add(packageEntity);
        for (Member member : pakage.getMembers()) {
            member.accept(this);
        }
        List<MemberEntity> memberEntities = new ArrayList<>();
        MemberEntity memberEntity = this.memberEntities.peek();
        while (!packageEntity.equals(memberEntity)) {
            memberEntities.add(this.memberEntities.pop());
            memberEntity = this.memberEntities.peek();
        }
        packageEntity.setMemberEntities(memberEntities);
        this.packageDao.save(packageEntity);
    }

    @Override
    public void visit(Class clazz) {
        if (clazz.getId() == null) {
            this.createClassEntity(clazz);
        } else {
            Optional<ClassEntity> optionalClassEntity = this.classDao.findById(clazz.getId());
            if (optionalClassEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.MEMBER_NOT_FOUND, clazz.getName());
            } else {
                this.memberEntities.add(optionalClassEntity.get());
            }
        }
    }

    private void createClassEntity(Class clazz) {
        ClassEntity classEntity = new ClassEntity(clazz);
        classEntity = this.classDao.save(classEntity);
        this.memberEntities.add(classEntity);
    }

    @Override
    public void visit(Use use) {
        if (use.getId() == null) {
            this.createUseEntity(use);
        } else {
            Optional<UseEntity> optionalUseEntity = this.useDao.findById(use.getId());
            if (optionalUseEntity.isEmpty()) {
                throw new PersistenceException(ErrorMessage.RELATION_NOT_FOUND);
            } else {
                this.relationEntities.add(optionalUseEntity.get());
            }
        }
    }

    private void createUseEntity(Use use) {
        UseEntity useEntity = new UseEntity(use,
                new MemberEntityFinder(this.packageDao, this.classDao, this.accountDao).find(use.getTarget()));
        useEntity = this.useDao.save(useEntity);
        this.relationEntities.add(useEntity);
    }

}
