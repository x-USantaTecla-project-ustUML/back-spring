package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence.utils;

import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.*;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.classDiagram.ClassDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.classDiagram.EnumDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.classDiagram.InterfaceDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.useCaseDiagram.ActorDao;
import com.usantatecla.ustumlserver.infrastructure.mongodb.daos.useCaseDiagram.UseCaseDao;
import org.springframework.beans.factory.annotation.Autowired;

abstract class WithMemberDaosPersistence {
    @Autowired
    protected AccountDao accountDao;
    @Autowired
    protected PackageDao packageDao;
    @Autowired
    protected ProjectDao projectDao;
    @Autowired
    protected ClassDao classDao;
    @Autowired
    protected InterfaceDao interfaceDao;
    @Autowired
    protected EnumDao enumDao;
    @Autowired
    protected ActorDao actorDao;
    @Autowired
    protected UseCaseDao useCaseDao;

}
