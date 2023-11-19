package ru.fita.domix.domain.calculator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.step.StepMapper;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalculatorMapper{
    private final StepMapper stepMapper;


    public CalculatorOutput toDto(Calculator model) {
        CalculatorOutput output = new CalculatorOutput();
        output.setId(model.getId());
        output.setName(model.getName());
        output.setStatus(model.getStatus());
        if (model.getCalculatorSteps() == null) {
            return output;
        }
        output.setSteps(
                model.getCalculatorSteps()
                        .stream()
                        .map(x -> stepMapper.toDto(x.getStep()))
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
        return output;
    }

    public CalculatorOutput mapToDtoWithAreaAndFloors(Calculator model, int area, int floors) {
        CalculatorOutput output = new CalculatorOutput();
        output.setId(model.getId());
        output.setName(model.getName());
        output.setStatus(model.getStatus());
        if (model.getCalculatorSteps() == null) {
            return output;
        }
        output.setSteps(
                model.getCalculatorSteps()
                        .stream()
                        .map(x -> stepMapper.toDtoWithAreaAndFloors(x.getStep(), area, floors))
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
        return output;
    }


    public Set<OnlyStepOutput> mapStepToOnlyStepList(Set<CalculatorStep> calculatorSteps) {
        Set<OnlyStepOutput> stepOutputs = new LinkedHashSet<>();
        for (CalculatorStep calculatorStep : calculatorSteps) {
            stepOutputs.add(stepMapper.mapToOnlyStepOutput(calculatorStep.getStep()));
        }
        return stepOutputs;
    }
}
