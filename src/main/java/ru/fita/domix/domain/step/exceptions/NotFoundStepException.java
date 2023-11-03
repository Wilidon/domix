package ru.fita.domix.domain.step.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundStepException extends RuntimeException{

    public NotFoundStepException() {
    }
    public NotFoundStepException(String message) {
        super(message);
    }
}
