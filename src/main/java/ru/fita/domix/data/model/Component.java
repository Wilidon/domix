package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 2)
    @Max(value = 64)
    private String title;

    @NotNull
    @NotBlank
    private String imageUrl;

    @Min(value = 0)
    private float price;

    @Min(value = 0)
    private float jobPrice;

    @ManyToOne
    @JoinColumn(name = "step_id")
    @JsonBackReference
    private Step step;


}
