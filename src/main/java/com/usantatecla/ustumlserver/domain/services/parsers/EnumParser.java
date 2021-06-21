package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.Member;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Enum;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EnumParser extends ClassParser {

    private static final String OBJECTS_KEY = "objects";
    private static final String OBJECT_KEY = "object";

    private List<String> objects;

    public EnumParser(Account account) {
        super(account);
        this.objects = new ArrayList<>();
    }

    @Override
    public Member get(Command command) {
        Enum _enum = (Enum) super.get(command);
        if (command.has(EnumParser.OBJECTS_KEY)) {
            this.parseObjects(command);
        }
        _enum.setObjects(this.objects);
        return _enum;
    }

    private void parseObjects(Command command) {
        for (Command objectCommand : command.getCommands(EnumParser.OBJECTS_KEY)) {
            String object = objectCommand.getString(EnumParser.OBJECT_KEY);
            if (Member.matchesName(object)) {
                this.objects.add(object);
            } else {
                throw new ParserException(ErrorMessage.INVALID_NAME, object);
            }
        }
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
