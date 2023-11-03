package ru.fita.domix.domain.step.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class AlreadyUsingException extends RuntimeException{
    public AlreadyUsingException() {
    }
}
