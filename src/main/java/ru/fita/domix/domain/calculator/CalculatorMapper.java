package ru.fita.domix.domain.calculator;

import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.util.DtoMapper;

@Service
public class CalculatorMapper implements DtoMapper<Calculator, CalculatorOutput> {
    @Override
    public CalculatorOutput toDto(Calculator model) {
        CalculatorOutput output = new CalculatorOutput();
        output.setId(model.getId());
        output.setName(model.getName());
        output.setStatus(model.getStatus());

        return output;
    }
}
