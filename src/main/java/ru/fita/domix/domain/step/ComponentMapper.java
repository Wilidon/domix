package ru.fita.domix.domain.step;

import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Component;
import ru.fita.domix.domain.step.dto.ComponentOutput;
import ru.fita.domix.domain.util.DtoMapper;

@Service
public class ComponentMapper implements DtoMapper<Component, ComponentOutput> {
    @Override
    public ComponentOutput toDto(Component model) {
        ComponentOutput componentOutput = new ComponentOutput();
        componentOutput.setId(model.getId());
        componentOutput.setTitle(model.getTitle());
        componentOutput.setImageUrl(model.getImageUrl());
        componentOutput.setPrice(model.getPrice());
        componentOutput.setJobPrice(model.getJobPrice());
        return componentOutput;
    }
}
