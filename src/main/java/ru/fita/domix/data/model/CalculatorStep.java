package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatorStep that = (CalculatorStep) o;
        return order == that.order && Objects.equals(id, that.id) && Objects.equals(step, that.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, step, order);
    }
}