package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.classDiagram.Attribute;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Method;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassPersistence extends MemberPersistence {
    Class read(String id);
    Class update(Class clazz);
}
