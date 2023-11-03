package ru.fita.domix.domain.calculator.dto;


import lombok.Data;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.domain.step.dto.StepOutput;

import java.util.Set;

@Data
public class CalculatorOutput {
    private long id;
    private String name;
    private CalculatorStatus status;
    private Set<StepOutput> steps;
}
