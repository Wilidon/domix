package ru.fita.domix.domain.component;

import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Component;
import ru.fita.domix.domain.component.dto.ComponentOutput;

@Service
public class ComponentMapper {
    public ComponentOutput toDto(Component model) {
        ComponentOutput componentOutput = new ComponentOutput();
        componentOutput.setId(model.getId());
        componentOutput.setDescription(model.getDescription());
        componentOutput.setTitle(model.getTitle());
        componentOutput.setImageUrl(model.getImageUrl());
        componentOutput.setPrice(model.getPrice());
        componentOutput.setJobPrice(model.getJobPrice());
        return componentOutput;
    }

    public ComponentOutput toDtoWithAreaAndFloors(Component model, int area, int floors) {
        // Цена компонента зависит от площади и количества этажей.
        // Берется общая площаль дома и делится на расход (consumption).
        // Получаем количество необходимого материала. И умножает на цену за штуку.
        float price = (float) area / model.getConsumption() * model.getPrice() * floors;
        float jobPrice = (float) area / model.getConsumption() * model.getJobPrice() * floors;

        ComponentOutput componentOutput = new ComponentOutput();
        componentOutput.setId(model.getId());
        componentOutput.setDescription(model.getDescription());
        componentOutput.setTitle(model.getTitle());
        componentOutput.setImageUrl(model.getImageUrl());
        componentOutput.setPrice(price);
        componentOutput.setJobPrice(jobPrice);
        return componentOutput;
    }
}
