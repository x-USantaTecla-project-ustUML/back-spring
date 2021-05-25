package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.MemberVisitor;
import com.usantatecla.ustumlserver.domain.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPersistence extends MemberVisitor {
    Project read(String id);
    void update(Project project);
}
