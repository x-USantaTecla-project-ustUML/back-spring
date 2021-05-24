package com.usantatecla.ustumlserver.infrastructure.mongodb.persistence;

import com.usantatecla.ustumlserver.domain.services.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PersistenceException extends RuntimeException {

    static final String DESCRIPTION = "Persistence exception";

    public PersistenceException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public PersistenceException(Error error, String context) {
        this(String.format(error.getDetail(), "\"" + context + "\""));
    }

    public PersistenceException(Error error) {
        this(DESCRIPTION + ". " + error.getDetail());
    }

}
