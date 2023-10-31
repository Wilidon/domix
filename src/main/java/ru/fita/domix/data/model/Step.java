package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    private String title;

    @Column(name = "\"order\"")
    @OrderBy("\"order\" ASC")
    private short order;

    private boolean multipleSelect;

    @OneToMany(mappedBy = "step")
    @JsonManagedReference
    private Set<Component> components;

    @ManyToOne
    @JoinColumn(name = "calculator_id")
    @JsonBackReference
    private Calculator calculator;
}
