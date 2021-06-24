package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils;

import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.relations.*;
import org.springframework.beans.factory.annotation.Autowired;

abstract class WithRelationDaosPersistence {
    @Autowired
    protected UseDao useDao;
    @Autowired
    protected CompositionDao compositionDao;
    @Autowired
    protected InheritanceDao inheritanceDao;
    @Autowired
    protected AggregationDao aggregationDao;
    @Autowired
    protected AssociationDao associationDao;
    @Autowired
    protected MemberEntityFinder memberEntityFinder;
}
