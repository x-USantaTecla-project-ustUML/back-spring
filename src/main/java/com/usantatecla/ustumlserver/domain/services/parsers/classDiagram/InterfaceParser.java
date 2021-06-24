package com.usantatecla.ustumlserver.domain.services.parsers.classDiagram;

import com.usantatecla.ustumlserver.domain.model.Account;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Interface;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InterfaceParser extends ClassParser {

    public InterfaceParser(Account account) {
        super(account);
    }

    @Override
    protected Interface createClass() {
        return new Interface(this.name, this.modifiers, this.attributes);
    }

    @Override
    public InterfaceParser copy(Account account) {
        return new InterfaceParser(account);
    }

}
