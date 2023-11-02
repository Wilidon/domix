package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 2)
    @NotNull
    @NotBlank
    private String title;

    @Column(name = "\"order\"")
    @OrderBy("\"order\" ASC")
    private short order;

    private boolean multipleSelect = false;

    @OneToMany(mappedBy = "step")
    @JsonManagedReference
    private Set<Component> components;

    @ManyToOne
    @JoinColumn(name = "calculator_id")
    @JsonBackReference
    private Calculator calculator;
}
