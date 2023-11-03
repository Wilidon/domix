package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
public class CalculatorStep {
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

    @Column(name = "\"order\"")
    private short order;
}