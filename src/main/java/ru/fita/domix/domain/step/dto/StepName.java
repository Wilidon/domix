package ru.fita.domix.domain.step.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class StepName {
    @NotBlank
    private String title;
}
