package ru.fita.domix.domain.step.dto;


import lombok.Data;

import java.util.Set;

@Data
public class StepOutput {
    private long id;
    private String title;
    private boolean multipleSelect;
    private Set<ComponentOutput> components;
}
