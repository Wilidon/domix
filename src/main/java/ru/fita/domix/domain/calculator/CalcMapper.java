package ru.fita.domix.domain.calculator;

import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.*;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.step.dto.ComponentOutput;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepOutput;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CalcMapper {
    public ComponentOutput mapToComponentOutput(Component component) {
        ComponentOutput componentOutput = new ComponentOutput();
        componentOutput.setId(component.getId());
        componentOutput.setTitle(component.getTitle());
        componentOutput.setImageUrl(component.getImageUrl());
        componentOutput.setPrice(component.getPrice());
        componentOutput.setJobPrice(component.getJobPrice());
        return componentOutput;
    }

    public Set<ComponentOutput> mapComponentToList(Set<StepComponent> components) {
        Set<ComponentOutput> componentOutputs = new LinkedHashSet<>();
        for (StepComponent component : components) {
            componentOutputs.add(mapToComponentOutput(component.getComponent()));
        }
        return componentOutputs;
    }

    public StepOutput mapToStepOutput(Step step) {
        StepOutput stepOutput = new StepOutput();
        stepOutput.setId(step.getId());
        stepOutput.setTitle(step.getTitle());
        stepOutput.setDescription(step.getDescription());
        stepOutput.setMultipleSelect(step.isMultipleSelect());

        stepOutput.setComponents(this.mapComponentToList(step.getStepComponents()));

        return stepOutput;
    }

    public Set<StepOutput> mapStepToList(Set<CalculatorStep> calculatorSteps) {
        Set<StepOutput> stepOutputs = new LinkedHashSet<>();
        for (CalculatorStep calculatorStep : calculatorSteps) {
            stepOutputs.add(mapToStepOutput(calculatorStep.getStep()));
        }
        return stepOutputs;
    }

    public CalculatorOutput mapToCalcOutput(Calculator calculator) {
        CalculatorOutput calculatorOutput = new CalculatorOutput();
        calculatorOutput.setId(calculator.getId());
        calculatorOutput.setName(calculator.getName());
        calculatorOutput.setStatus(calculator.getStatus());
        calculatorOutput.setSteps(mapStepToList(calculator.getCalculatorSteps()));
        return calculatorOutput;
    }

    public OnlyStepOutput mapToOnlyStepOutput(Step step) {
        OnlyStepOutput stepOutput = new OnlyStepOutput();
        stepOutput.setId(step.getId());
        stepOutput.setTitle(step.getTitle());
        stepOutput.setDescription(step.getDescription());
        stepOutput.setMultipleSelect(step.isMultipleSelect());

        return stepOutput;
    }

    public Set<OnlyStepOutput> mapStepToOnlyStepList(Set<CalculatorStep> calculatorSteps) {
        Set<OnlyStepOutput> stepOutputs = new LinkedHashSet<>();
        for (CalculatorStep calculatorStep : calculatorSteps) {
            stepOutputs.add(mapToOnlyStepOutput(calculatorStep.getStep()));
        }
        return stepOutputs;
    }

}
