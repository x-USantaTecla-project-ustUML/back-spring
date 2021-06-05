package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.Relation;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


@AllArgsConstructor
@Data
public class RelationParser {

    public static final String ROLE_KEY = "role";

    public Relation get(Account account, Command command) {
        String targetName = command.getTargetName();
        Member target = account.findRoute(getStackRoute(targetName));
        if (target == null) {
            throw new ParserException(ErrorMessage.INVALID_ROUTE, targetName);
        }
        String role = null;
        if (command.has(RelationParser.ROLE_KEY)) {
            role = command.getString(RelationParser.ROLE_KEY);
        }
        return command.getRelationType().create(target, role);
    }

    private Stack<String> getStackRoute(String route) {
        Stack<String> stackPath = new Stack<>();
        List<String> splitPath = Arrays.asList(route.split("\\."));
        Collections.reverse(splitPath);
        stackPath.addAll(splitPath);
        return stackPath;
    }

}
