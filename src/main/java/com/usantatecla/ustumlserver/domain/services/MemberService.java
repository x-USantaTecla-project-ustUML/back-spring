package com.usantatecla.ustumlserver.domain.services;

import com.usantatecla.ustumlserver.domain.model.Member;
import org.springframework.stereotype.Service;

@Service
abstract class MemberService {

    abstract Member add(Command command);

    public abstract MemberService clone();

}
