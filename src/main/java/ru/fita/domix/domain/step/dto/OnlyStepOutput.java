package ru.fita.domix.domain.step.dto;

import lombok.Data;

@Data
public class OnlyStepOutput {
    private long id;
    private String title;
    private String description;
    private boolean multipleSelect;
}
