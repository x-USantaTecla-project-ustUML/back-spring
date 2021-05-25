package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PersistenceException extends RuntimeException {

    static final String DESCRIPTION = "Persistence exception";

    public PersistenceException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public PersistenceException(ErrorMessage errorMessage, String context) {
        this(String.format(errorMessage.getDetail(), "\"" + context + "\""));
    }

    public PersistenceException(ErrorMessage errorMessage) {
        this(DESCRIPTION + ". " + errorMessage.getDetail());
    }

}
