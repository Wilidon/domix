package ru.fita.domix.domain.step;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.domain.component.ComponentMapper;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepOutput;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StepMapper {
    private final ComponentMapper componentMapper;


    public StepOutput toDto(Step model) {
        StepOutput stepOutput = new StepOutput();
        stepOutput.setId(model.getId());
        stepOutput.setTitle(model.getTitle());
        stepOutput.setDescription(model.getDescription());
        stepOutput.setMultipleSelect(model.isMultipleSelect());
        stepOutput.setComponents(
                model.getStepComponents()
                        .stream()
                        .map(x -> componentMapper.toDto(x.getComponent())
                        )
                        .collect(Collectors.toCollection(LinkedHashSet::new)));
        return stepOutput;
    }

    public StepOutput toDtoWithAreaAndFloors(Step model, int area, int floors) {
        StepOutput stepOutput = new StepOutput();
        stepOutput.setId(model.getId());
        stepOutput.setTitle(model.getTitle());
        stepOutput.setDescription(model.getDescription());
        stepOutput.setMultipleSelect(model.isMultipleSelect());
        stepOutput.setComponents(
                model.getStepComponents()
                        .stream()
                        .map(x -> componentMapper.toDtoWithAreaAndFloors(x.getComponent(), area, floors)
                        )
                        .collect(Collectors.toCollection(LinkedHashSet::new)));
        return stepOutput;
    }

    public OnlyStepOutput mapToOnlyStepOutput(Step step) {
        OnlyStepOutput stepOutput = new OnlyStepOutput();
        stepOutput.setId(step.getId());
        stepOutput.setTitle(step.getTitle());
        stepOutput.setDescription(step.getDescription());
        stepOutput.setMultipleSelect(step.isMultipleSelect());

        return stepOutput;
    }
}
