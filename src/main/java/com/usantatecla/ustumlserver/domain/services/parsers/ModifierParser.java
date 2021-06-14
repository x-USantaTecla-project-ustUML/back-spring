package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.Class;
import com.usantatecla.ustumlserver.domain.model.Modifier;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.Command;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifierParser {

    List<Modifier> modifiers;

    public ModifierParser() {
        this.modifiers = new ArrayList<>();
    }

    public List<Modifier> get(Command command) {
        String modifiers = command.getString(ClassParser.MODIFIERS_KEY);
        if (Class.matchesModifiers(modifiers)) {
            List<String> splitModifiers = new ArrayList<>(Arrays.asList(modifiers.split(" ")));
            splitModifiers.removeIf(""::equals);
            for (String modifier : splitModifiers) {
                Modifier modifierToUpdate = Modifier.get(modifier);
                if(!this.modifiers.contains(modifierToUpdate)){
                    this.modifiers.add(modifierToUpdate);
                }
            }
            return this.modifiers;
        } else {
            throw new ParserException(ErrorMessage.INVALID_CLASS_MODIFIERS, modifiers);
        }
    }
}
