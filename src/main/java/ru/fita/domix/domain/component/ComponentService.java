package ru.fita.domix.domain.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Component;
import ru.fita.domix.data.repository.ComponentRepository;
import ru.fita.domix.data.repository.StepComponentRepository;
import ru.fita.domix.domain.calculator.CalcMapper;
import ru.fita.domix.domain.component.dto.ComponentInput;
import ru.fita.domix.domain.component.exceptions.NotFoundComponentException;
import ru.fita.domix.domain.component.dto.ComponentOutput;
import ru.fita.domix.domain.step.exceptions.AlreadyUsingException;
import ru.fita.domix.domain.util.DtoMapper;

@Service
public class ComponentService {
    private final ComponentRepository componentRepository;

    private final CalcMapper calcMapper;

    private final DtoMapper<Component, ComponentOutput> outputMapper;

    private final StepComponentRepository stepComponentRepository;

    @Autowired
    public ComponentService(ComponentRepository componentRepository, CalcMapper calcMapper,
                            @Qualifier("componentMapper") DtoMapper<Component, ComponentOutput> outputMapper, StepComponentRepository stepComponentRepository) {
        this.componentRepository = componentRepository;
        this.calcMapper = calcMapper;
        this.outputMapper = outputMapper;
        this.stepComponentRepository = stepComponentRepository;
    }

    public ComponentOutput createComponent(ComponentInput componentInput){
        Component component = new Component();
        component.setTitle(componentInput.getTitle());
        component.setDescription(componentInput.getDescription());
        component.setPrice(componentInput.getPrice());
        component.setImageUrl(componentInput.getImageUrl());
        component.setJobPrice(componentInput.getJobPrice());
        componentRepository.save(component);
        return calcMapper.mapToComponentOutput(component);
    }

    public ComponentOutput getComponent(long componentId) {
        return outputMapper.toDto(componentRepository.findById(componentId).orElseThrow(NotFoundComponentException::new));
    }

    public ComponentOutput deleteComponent(long componentId){
        Component component = componentRepository.findById(componentId).orElseThrow(NotFoundComponentException::new);
        if (stepComponentRepository.findAllByComponentId(componentId).isEmpty()) {
            componentRepository.delete(component);
            return outputMapper.toDto(component);
        }
        throw (new AlreadyUsingException());
    }

}
