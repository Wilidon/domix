package ru.fita.domix.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Calculator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CalculatorStatus status = CalculatorStatus.ACTIVE;

    @OneToMany(mappedBy = "calculator")
    private Set<Step> steps;



}
