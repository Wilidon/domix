package ru.fita.domix.domain.calculator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.fita.domix.domain.exceptions.NotFoundException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundCalculatorException extends NotFoundException {
    public NotFoundCalculatorException() {
        super();
    }
    public NotFoundCalculatorException(String message) {
        super(message);
    }

    public NotFoundCalculatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
