package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class RelationParser {

    public static final String ROLE_KEY = "role";
    static final String SET_KEY = "set";

    public Relation get(Account account, Command command) {
        String targetName = command.getTargetName();
        Member target = account.findRoute(targetName);
        if (target == null) {
            throw new ParserException(ErrorMessage.INVALID_ROUTE, targetName);
        }
        String role = null;
        if (command.has(RelationParser.ROLE_KEY)) {
            role = command.getString(RelationParser.ROLE_KEY);
        }
        return command.getRelationType().create(target, role);
    }

    public Relation getModifiedRelation(Account account, Command command) {
        if (!command.has(RelationParser.SET_KEY)) {
            throw new ParserException(ErrorMessage.KEY_NOT_FOUND, RelationParser.SET_KEY);
        }
        String targetName = command.getString(MemberParser.SET_KEY);
        if (targetName == null) {
            throw new ParserException(ErrorMessage.INVALID_VALUE, null);
        }
        Member target = account.findRoute(targetName);
        if (target == null) {
            throw new ParserException(ErrorMessage.INVALID_ROUTE, targetName);
        }
        String role = null;
        if (command.has(RelationParser.ROLE_KEY)) {
            role = command.getString(RelationParser.ROLE_KEY);
        }
        return command.getRelationType().create(target, role);
    }

}
