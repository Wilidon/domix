package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Calculator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private CalculatorStatus status = CalculatorStatus.ACTIVE;

    @JsonIgnore
    @OneToMany(mappedBy = "calculator")
    private Set<CalculatorSteps> calculatorSteps;



}
