package com.usantatecla.ustumlserver.domain.services.parsers.useCaseDiagram;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;
import com.usantatecla.ustumlserver.domain.services.parsers.MemberParser;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UseCaseParser  extends MemberParser {

    public UseCaseParser(Account account) {
        super(account);
    }

    @Override
    public Member get(Command command) {
        this.parseName(command.getMemberName());
        UseCase useCase = this.createUseCase();
        this.addRelations(command, useCase);
        return useCase;
    }

    protected UseCase createUseCase() {
        return new UseCase(this.name);
    }

    @Override
    public MemberParser copy(Account account) {
        return new UseCaseParser(account);
    }

}
