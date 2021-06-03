package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Class;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassPersistence {
    Class read(String id);
    void update(Class clazz);
}
