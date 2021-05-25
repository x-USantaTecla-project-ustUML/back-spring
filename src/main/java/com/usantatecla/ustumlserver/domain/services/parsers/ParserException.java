package com.usantatecla.ustumlserver.domain.services.parsers;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ParserException extends RuntimeException {

    static final String DESCRIPTION = "Command exception";

    public ParserException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public ParserException(ErrorMessage errorMessage, String context) {
        this(String.format(errorMessage.getDetail(), "\"" + context + "\""));
    }

    public ParserException(ErrorMessage errorMessage) {
        this(DESCRIPTION + ". " + errorMessage.getDetail());
    }

}
