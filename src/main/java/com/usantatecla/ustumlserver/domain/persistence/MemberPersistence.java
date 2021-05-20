package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPersistence {

    Member read(String name);

}
