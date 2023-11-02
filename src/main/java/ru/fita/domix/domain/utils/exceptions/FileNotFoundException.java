package ru.fita.domix.domain.utils.exceptions;

import ru.fita.domix.domain.exceptions.NotFoundException;


public class FileNotFoundException extends NotFoundException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
