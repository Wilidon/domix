package ru.fita.domix.domain.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.step.dto.StepOutput;
import ru.fita.domix.domain.util.DtoMapper;

import java.util.stream.Collectors;

@Service
public class CalculatorMapper implements DtoMapper<Calculator, CalculatorOutput> {
    private final DtoMapper<Step, StepOutput> stepMapper;

    @Autowired
    public CalculatorMapper(@Qualifier("stepMapper") DtoMapper<Step, StepOutput> stepMapper) {
        this.stepMapper = stepMapper;
    }
    @Override
    public CalculatorOutput toDto(Calculator model) {
        CalculatorOutput output = new CalculatorOutput();
        output.setId(model.getId());
        output.setName(model.getName());
        output.setStatus(model.getStatus());
        output.setSteps(
                model.getCalculatorSteps()
                        .stream()
                        .map(
                                x -> stepMapper.toDto(
                                        x.getStep()
                                )
                        )
                        .collect(Collectors.toSet()));
        return output;
    }
}
