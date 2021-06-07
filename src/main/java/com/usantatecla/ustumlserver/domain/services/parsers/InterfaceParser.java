package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Interface;

public class InterfaceParser extends ClassParser {

    @Override
    protected Interface createClass() {
        return new Interface(this.name, this.modifiers, this.attributes);
    }

    @Override
    public InterfaceParser copy() {
        return new InterfaceParser();
    }
}
