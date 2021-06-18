package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberEntityDeleter extends WithMemberDaosPersistence implements MemberVisitor {

    private MemberEntityFinder memberEntityFinder;
    private RelationEntityDeleter relationEntityDeleter;

    @Autowired
    public MemberEntityDeleter(MemberEntityFinder memberEntityFinder, RelationEntityDeleter relationEntityDeleter) {
        this.memberEntityFinder = memberEntityFinder;
        this.relationEntityDeleter = relationEntityDeleter;
    }

    void delete(Member member) {
        member.accept(this);
    }

    @Override
    public void visit(Account account) {
        this.deleteRelations(account);
        for (Project project : account.getProjects()) {
            project.accept(this);
        }
        this.accountDao.deleteById(account.getId());
    }

    @Override
    public void visit(Package pakage) {
        this.deleteRelations(pakage);
        for (Member member : pakage.getMembers()) {
            member.accept(this);
        }
        this.packageDao.deleteById(pakage.getId());
    }

    @Override
    public void visit(Project project) {
        this.deleteRelations(project);
        for (Member member : project.getMembers()) {
            member.accept(this);
        }
        this.projectDao.deleteById(project.getId());
    }

    @Override
    public void visit(Class clazz) {
        this.deleteRelations(clazz);
        this.classDao.deleteById(clazz.getId());
    }

    @Override
    public void visit(Interface _interface) {
        this.deleteRelations(_interface);
        this.interfaceDao.deleteById(_interface.getId());
    }

    @Override
    public void visit(Enum _enum) {
        this.deleteRelations(_enum);
        this.enumDao.deleteById(_enum.getId());
    }

    private void deleteRelations(Member member) {
        for (Relation relation : member.getRelations()) {
            this.relationEntityDeleter.delete(relation);
        }
        this.relationEntityDeleter.deleteAll(member);
    }

}
