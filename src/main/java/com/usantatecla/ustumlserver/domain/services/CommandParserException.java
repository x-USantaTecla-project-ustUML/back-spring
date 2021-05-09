package com.usantatecla.ustumlserver.domain.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CommandParserException extends RuntimeException {

    static final String DESCRIPTION = "Command exception";

    public CommandParserException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public CommandParserException(Error error, String context) {
        this(String.format(error.getDetail(), "\"" + context + "\""));
    }

    public CommandParserException(Error error) {
        this(DESCRIPTION + ". " + error.getDetail());
    }

}
