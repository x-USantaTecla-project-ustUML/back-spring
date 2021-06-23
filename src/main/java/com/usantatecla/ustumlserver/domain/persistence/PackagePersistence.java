package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagePersistence {
    Package read(String id);
    Package deleteMembers(Package pakage, List<Member> members);
}
