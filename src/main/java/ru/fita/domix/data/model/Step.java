package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    @Column(length = 2500, nullable = false)
    private String description;

    private boolean multipleSelect = false;

    @OneToMany(mappedBy = "step")
    @JsonManagedReference
    @OrderBy("order asc")
    private Set<StepComponent> stepComponents;

    @OneToMany(mappedBy = "step")
    @JsonIgnore
    private Set<CalculatorStep> calculatorSteps;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return id == step.id && multipleSelect == step.multipleSelect && Objects.equals(title, step.title) && Objects.equals(description, step.description) && Objects.equals(stepComponents, step.stepComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, multipleSelect, stepComponents);
    }
}
