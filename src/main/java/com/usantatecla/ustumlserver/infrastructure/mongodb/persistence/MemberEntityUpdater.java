package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UseDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.ClassEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.MemberEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.PackageEntity;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.RelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Repository
public class MemberEntityUpdater extends WithDaosPersistence implements MemberVisitor {

    private UseDao useDao;
    private Stack<MemberEntity> memberEntities;
    private List<RelationEntity> relationEntities;
    private MemberEntityFinder memberEntityFinder;
    private RelationEntityUpdater relationEntityUpdater;

    @Autowired
    public MemberEntityUpdater(AccountDao accountDao, PackageDao packageDao, ClassDao classDao, UseDao useDao, MemberEntityFinder memberEntityFinder, RelationEntityUpdater relationEntityUpdater) {
        super(accountDao, packageDao, classDao);
        this.useDao = useDao;
        this.memberEntities = new Stack<>();
        this.relationEntities = new ArrayList<>();
        this.memberEntityFinder = memberEntityFinder;
        this.relationEntityUpdater = relationEntityUpdater;
    }

    PackageEntity update(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        if (pakage.getId() != null) {
            packageEntity = (PackageEntity) this.memberEntityFinder.find(pakage);
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
            this.relationEntities.add(this.relationEntityUpdater.update(relation));
        }
        List<RelationEntity> relationEntities = new ArrayList<>(this.relationEntities);
        this.relationEntities.clear();
        return relationEntities;
    }

    @Override
    public void visit(Account account) {

    }

    @Override
    public void visit(Package pakage) {
        PackageEntity packageEntity = new PackageEntity(pakage);
        if (pakage.getId() != null) {
            packageEntity = (PackageEntity) this.memberEntityFinder.find(pakage);
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
        ClassEntity classEntity;
        if (clazz.getId() == null) {
            classEntity = new ClassEntity(clazz);
            classEntity = this.classDao.save(classEntity);
        } else {
            classEntity = (ClassEntity) this.memberEntityFinder.find(clazz);
        }
        this.memberEntities.add(classEntity);
    }

}
