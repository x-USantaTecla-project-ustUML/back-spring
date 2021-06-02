package com.usantatecla.ustumlserver.domain.model;

import com.usantatecla.ustumlserver.infrastructure.api.dtos.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ModelException extends RuntimeException {

    static final String DESCRIPTION = "Model exception";

    public ModelException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public ModelException(ErrorMessage errorMessage, String context) {
        this(String.format(errorMessage.getDetail(), "\"" + context + "\""));
    }

    public ModelException(ErrorMessage errorMessage) {
        this(DESCRIPTION + ". " + errorMessage.getDetail());
    }

}
