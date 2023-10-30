package ru.fita.domix.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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

    private String title;

    private float price;

    private float jobPrice;

    @ManyToOne
    @JoinColumn(name = "step_id")
    @JsonBackReference
    private Step step;


}
