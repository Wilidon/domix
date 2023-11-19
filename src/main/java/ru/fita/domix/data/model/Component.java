package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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


}
