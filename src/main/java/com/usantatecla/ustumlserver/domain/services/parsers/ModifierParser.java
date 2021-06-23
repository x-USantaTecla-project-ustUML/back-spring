package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.domain.model.classDiagram.Class;
import com.usantatecla.ustumlserver.domain.model.classDiagram.Modifier;
import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifierParser {

    public List<Modifier> get(String modifiersString) {
        List<Modifier> modifiers = new ArrayList<>();
        if (Class.matchesModifiers(modifiersString)) {
            List<String> splitModifiers = new ArrayList<>(Arrays.asList(modifiersString.split(" ")));
            splitModifiers.removeIf(""::equals);
            for (String modifier : splitModifiers) {
                modifiers.add(Modifier.get(modifier));
            }
        } else {
            throw new ParserException(ErrorMessage.INVALID_CLASS_MODIFIERS, modifiersString);
        }
        return modifiers;
    }

}
