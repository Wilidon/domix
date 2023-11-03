package ru.fita.domix.domain.calculator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundCalculatorException extends RuntimeException {
    public NotFoundCalculatorException() {
    }

    public NotFoundCalculatorException(String message) {
        super(message);
    }
}
