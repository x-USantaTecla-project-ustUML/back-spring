package com.usantatecla.ustumlserver.domain.model.generators;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Package;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.Actor;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;

public abstract class Generator {

    public String generate(Member member) {
        return member.accept(this);
    }

    public abstract String visit(Account account);

    public abstract String visit(Package pakage);

    public abstract String visit(Class clazz);

    public abstract String visit(Enum _enum);

    public abstract String visit(Actor actor);

    public abstract String visit(UseCase useCase);
}
