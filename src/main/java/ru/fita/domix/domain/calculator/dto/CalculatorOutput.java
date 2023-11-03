package ru.fita.domix.domain.calculator.dto;

import lombok.Data;
import ru.fita.domix.data.model.CalculatorStatus;

@Data
public class CalculatorOutput {
    private long id;
    private String name;
    private CalculatorStatus status;
}
