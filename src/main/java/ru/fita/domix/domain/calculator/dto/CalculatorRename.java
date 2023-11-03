package ru.fita.domix.domain.calculator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CalculatorRename {
    private long id;
    @NotBlank
    private String newName;
}
