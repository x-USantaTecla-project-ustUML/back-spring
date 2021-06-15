package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberEntityDeleter extends WithMemberDaosPersistence implements MemberVisitor {

    private MemberEntityFinder memberEntityFinder;
    private RelationEntityDeleter relationEntityDeleter;

    @Autowired
    public MemberEntityDeleter(MemberEntityFinder memberEntityFinder, RelationEntityDeleter relationEntityDeleter) {
        this.memberEntityFinder = memberEntityFinder;
        this.relationEntityDeleter = relationEntityDeleter;
    }

    MemberEntity delete(Member member, List<Member> members, List<Relation> relations) {
        for (Member memberItem: members) {
            memberItem.accept(this);
        }
        if (!relations.isEmpty()){
            this.deleteRelations(relations);
        }
        return this.memberEntityFinder.find(member);
    }

    @Override
    public void visit(Account account) {
    }

    @Override
    public void visit(Package pakage) {
        if (!pakage.getRelations().isEmpty()){
            this.deleteRelations(pakage.getRelations());
        }
        for (Member member: pakage.getMembers()) {
            member.accept(this);
        }
        this.packageDao.deleteById(pakage.getId());
    }

    @Override
    public void visit(Project project) {
        if (!project.getRelations().isEmpty()){
            this.deleteRelations(project.getRelations());
        }
        for (Member member: project.getMembers()) {
            member.accept(this);
        }
        this.projectDao.deleteById(project.getId());
    }

    @Override
    public void visit(Class clazz) {
        if (!clazz.getRelations().isEmpty()){
            this.deleteRelations(clazz.getRelations());
        }
        this.classDao.deleteById(clazz.getId());
    }

    @Override
    public void visit(Interface _interface) {
        this.deleteRelations(_interface.getRelations());
        this.classDao.deleteById(_interface.getId());
    }

    @Override
    public void visit(Enum _enum) {
        this.deleteRelations(_enum.getRelations());
        this.classDao.deleteById(_enum.getId());
    }

    private void deleteRelations(List<Relation> relations) {
        for (Relation relation : relations) {
            this.relationEntityDeleter.delete(relation);
        }
    }

}
