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
public class StepComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "step_id")
    private Step step;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    @Column(name = "\"order\"")
    private short order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepComponent that = (StepComponent) o;
        return order == that.order && Objects.equals(id, that.id) && Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, component, order);
    }
}