package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassPersistence extends MemberPersistence {
    Class read(String id);
    Class update(Class clazz);
}
