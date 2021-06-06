package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.AccountDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.PackageDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.UseDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberEntityUpdater extends WithDaosPersistence implements MemberVisitor {

    private MemberEntityFinder memberEntityFinder;
    private RelationEntityUpdater relationEntityUpdater;
    private MemberEntity memberEntity;

    @Autowired
    public MemberEntityUpdater(AccountDao accountDao, PackageDao packageDao, ClassDao classDao, MemberEntityFinder memberEntityFinder, RelationEntityUpdater relationEntityUpdater) {
        super(accountDao, packageDao, classDao);
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
        List<PackageEntity> packageEntities = new ArrayList<>();
        for (Project project : account.getProjects()) {
            project.accept(this);
            packageEntities.add((PackageEntity) this.memberEntity);
        }
        accountEntity.setPackageEntities(packageEntities);
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
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (Member member : pakage.getMembers()) {
            member.accept(this);
            memberEntities.add(this.memberEntity);
        }
        packageEntity.setMemberEntities(memberEntities);
        this.updateRelations(packageEntity, pakage.getRelations());
        this.memberEntity = this.packageDao.save(packageEntity);
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

    private void updateRelations(MemberEntity memberEntity, List<Relation> relations) {
        List<RelationEntity> relationEntities = new ArrayList<>();
        for (Relation relation : relations) {
            relationEntities.add(this.relationEntityUpdater.update(relation));
        }
        memberEntity.setRelationEntities(relationEntities);
    }

}
