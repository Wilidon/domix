package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(length = 500)
    private String description;

    @NotNull
    @NotBlank
    private String imageUrl;

    @Min(value = 0)
    private float price;

    @Min(value = 0)
    private float jobPrice;

    @Min(value = 0)
    @Column(nullable = true)
    private int consumption;

    @OneToMany
    @JoinColumn(name = "step_id")
    @JsonBackReference
    private Set<StepComponent> stepComponents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id && Float.compare(price, component.price) == 0 && Float.compare(jobPrice, component.jobPrice) == 0 && consumption == component.consumption && Objects.equals(title, component.title) && Objects.equals(description, component.description) && Objects.equals(imageUrl, component.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, imageUrl, price, jobPrice, consumption);
    }
}
