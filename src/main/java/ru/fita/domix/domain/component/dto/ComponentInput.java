package ru.fita.domix.domain.component.dto;


import lombok.Data;


@Data
public class ComponentInput {

    private String title;

    private String imageUrl;

    private float price;

    private float jobPrice;
}
