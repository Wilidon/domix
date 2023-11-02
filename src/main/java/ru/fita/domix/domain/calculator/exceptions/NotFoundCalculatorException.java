package ru.fita.domix.domain.calculator.exceptions;

public class NotFoundCalculatorException extends RuntimeException {
    public NotFoundCalculatorException() {
    }

    public NotFoundCalculatorException(String message) {
        super(message);
    }
}
