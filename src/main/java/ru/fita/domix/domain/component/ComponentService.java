package ru.fita.domix.domain.component;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Component;
import ru.fita.domix.data.repository.ComponentRepository;
import ru.fita.domix.data.repository.StepComponentRepository;
import ru.fita.domix.domain.component.dto.ComponentInput;
import ru.fita.domix.domain.component.dto.ComponentOutput;
import ru.fita.domix.domain.component.exceptions.NotFoundComponentException;
import ru.fita.domix.domain.step.exceptions.AlreadyUsingException;

@Service
@AllArgsConstructor
public class ComponentService {
    private final ComponentRepository componentRepository;

    private final ComponentMapper componentMapper;

    private final StepComponentRepository stepComponentRepository;


    public ComponentOutput createComponent(ComponentInput componentInput){
        Component component = new Component();
        component.setTitle(componentInput.getTitle());
        component.setDescription(componentInput.getDescription());
        component.setPrice(componentInput.getPrice());
        component.setImageUrl(componentInput.getImageUrl());
        component.setJobPrice(componentInput.getJobPrice());
        componentRepository.save(component);
        return componentMapper.toDto(component);
    }

    public ComponentOutput getComponent(long componentId) {
        return componentMapper.toDto(componentRepository.findById(componentId).orElseThrow(NotFoundComponentException::new));
    }

    public ComponentOutput deleteComponent(long componentId){
        Component component = componentRepository.findById(componentId).orElseThrow(NotFoundComponentException::new);
        if (stepComponentRepository.findAllByComponentId(componentId).isEmpty()) {
            componentRepository.delete(component);
            return componentMapper.toDto(component);
        }
        throw (new AlreadyUsingException());
    }

}
