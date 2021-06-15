package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Relation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagePersistence {
    Package read(String id);
    Package update(Package pakage);
    Package delete(Package pakage, List<Member> membersId, List<Relation> relations);
}
