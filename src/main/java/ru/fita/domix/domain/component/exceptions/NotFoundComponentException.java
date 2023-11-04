package ru.fita.domix.domain.component.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundComponentException extends RuntimeException{

    public NotFoundComponentException() {
    }
    public NotFoundComponentException(String message) {
        super(message);
    }
}
