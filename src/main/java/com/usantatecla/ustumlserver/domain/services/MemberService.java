package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;

interface MemberService {

    Member add(Command command);

}
