package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "CalculatorSteps")
@Getter
@Setter
public class CalculatorSteps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "calculator_id")
    private Calculator calculator;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Step step;

    private short order1;
}