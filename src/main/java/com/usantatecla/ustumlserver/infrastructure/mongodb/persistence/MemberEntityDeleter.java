package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.model.*;
import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Enum;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberEntityDeleter extends WithMemberDaosPersistence implements MemberVisitor {

    private MemberEntityFinder memberEntityFinder;
    private RelationEntityDeleter relationEntityDeleter;
    private UseDao useDao;
    private CompositionDao compositionDao;
    private InheritanceDao inheritanceDao;
    private AggregationDao aggregationDao;
    private AssociationDao associationDao;

    @Autowired
    public MemberEntityDeleter(MemberEntityFinder memberEntityFinder, RelationEntityDeleter relationEntityDeleter,
                               UseDao useDao, CompositionDao compositionDao, InheritanceDao inheritanceDao,
                               AggregationDao aggregationDao, AssociationDao associationDao) {
        this.memberEntityFinder = memberEntityFinder;
        this.relationEntityDeleter = relationEntityDeleter;
        this.useDao = useDao;
        this.compositionDao = compositionDao;
        this.inheritanceDao = inheritanceDao;
        this.aggregationDao = aggregationDao;
        this.associationDao = associationDao;
    }

    MemberEntity delete(Member member, List<Member> members, List<Relation> relations) {
        this.deleteEfferentRelations(relations);
        for (Member memberItem: members) {
            memberItem.accept(this);
        }
        return this.memberEntityFinder.find(member);
    }

    private void deleteEfferentRelations(List<Relation> relations) {
        for (Relation relation : relations) {
            this.relationEntityDeleter.delete(relation);
        }
    }

    @Override
    public void visit(Account account) {
    }

    @Override
    public void visit(Package pakage) {
        this.deleteRelations(pakage);
        for (Member member: pakage.getMembers()) {
            member.accept(this);
        }
        this.packageDao.deleteById(pakage.getId());
    }

    @Override
    public void visit(Project project) {
        this.deleteRelations(project);
        for (Member member: project.getMembers()) {
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
        this.deleteEfferentRelations(member.getRelations());

        MemberEntity memberEntity = this.memberEntityFinder.find(member);
        this.useDao.deleteAll(this.useDao.findByTarget(memberEntity));
        this.compositionDao.deleteAll(this.compositionDao.findByTarget(memberEntity));
        this.inheritanceDao.deleteAll(this.inheritanceDao.findByTarget(memberEntity));
        this.aggregationDao.deleteAll(this.aggregationDao.findByTarget(memberEntity));
        this.associationDao.deleteAll(this.associationDao.findByTarget(memberEntity));
    }

}
