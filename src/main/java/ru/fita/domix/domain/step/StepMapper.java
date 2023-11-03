package ru.fita.domix.domain.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Component;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.domain.step.dto.ComponentOutput;
import ru.fita.domix.domain.step.dto.StepOutput;
import ru.fita.domix.domain.util.DtoMapper;

import java.util.stream.Collectors;

@Service
public class StepMapper implements DtoMapper<Step, StepOutput> {
    private final DtoMapper<Component, ComponentOutput> componentMapper;

    @Autowired
    public StepMapper(@Qualifier("componentMapper") DtoMapper<Component, ComponentOutput> componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override
    public StepOutput toDto(Step model) {
        StepOutput stepOutput = new StepOutput();
        stepOutput.setId(model.getId());
        stepOutput.setTitle(model.getTitle());
        stepOutput.setMultipleSelect(model.isMultipleSelect());
        stepOutput.setComponents(
                model.getStepComponents()
                        .stream()
                        .map(
                                x -> componentMapper.toDto(
                                        x.getComponent()
                                )
                        )
                        .collect(Collectors.toSet()));
        return stepOutput;
    }
}
