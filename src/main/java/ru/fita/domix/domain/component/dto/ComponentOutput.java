package ru.fita.domix.domain.component.dto;

import lombok.Data;

@Data
public class ComponentOutput {
    private long id;
    private String title;
    private String description;
    private String imageUrl;
    private float price;
    private float jobPrice;
}
