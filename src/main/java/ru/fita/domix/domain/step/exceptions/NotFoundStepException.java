package ru.fita.domix.domain.step.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.fita.domix.domain.exceptions.NotFoundException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundStepException extends NotFoundException {

    public NotFoundStepException() {
    }
    public NotFoundStepException(String message) {
        super(message);
    }

    public NotFoundStepException(String message, Throwable cause) {
        super(message, cause);
    }
}
