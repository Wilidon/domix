package ru.fita.domix.domain.step.dto;

import lombok.Data;

@Data
public class ComponentOutput {
    private long id;
    private String title;
    private String imageUrl;
    private float price;
    private float jobPrice;
}
