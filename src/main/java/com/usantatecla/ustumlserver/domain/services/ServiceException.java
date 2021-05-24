package com.usantatecla.ustumlserver.domain.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ServiceException extends RuntimeException {

    static final String DESCRIPTION = "Service exception";

    public ServiceException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public ServiceException(Error error, String context) {
        this(String.format(error.getDetail(), "\"" + context + "\""));
    }

    public ServiceException(Error error) {
        this(DESCRIPTION + ". " + error.getDetail());
    }

}
