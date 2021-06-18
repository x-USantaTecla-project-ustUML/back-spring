package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.Relation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagePersistence extends MemberPersistence {
    Package read(String id);
    Package update(Package pakage);
    Package deleteMembers(Package pakage, List<Member> members);
}
