package com.usantatecla.ustumlserver.domain.services.parsers.classDiagram;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EnumParser extends ClassParser {

    private List<String> objects;

    public EnumParser(Account account) {
        super(account);
        this.objects = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        Enum _enum = (Enum) super.get(command);
        for (Command objectCommand : command.getCommands(Command.OBJECTS)) {
            _enum.addObject(new EnumObjectParser().get(objectCommand));
        }
        return _enum;
    }

    @Override
    protected Enum createClass() {
        return new Enum(this.name, this.modifiers, this.attributes);
    }

    @Override
    public EnumParser copy(Account account) {
        return new EnumParser(account);
    }

}
