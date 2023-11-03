package ru.fita.domix.domain.calculator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CalculatorInput {
    @NotBlank
    private String name;
}
