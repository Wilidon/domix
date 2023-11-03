package ru.fita.domix.domain.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.StepRepository;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;

@Service
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final CalcMapper mapper;

    @Autowired
    public CalculatorService(CalculatorRepository calculatorRepository, CalcMapper mapper) {
        this.calculatorRepository = calculatorRepository;
        this.mapper = mapper;
    }

    public CalculatorOutput getCalculator() {
        Calculator calculator = calculatorRepository.findByStatus(CalculatorStatus.ACTIVE)
                .orElseThrow(NotFoundCalculatorException::new);

        return mapper.mapToCalcOutput(calculator);

    }

//    public Calculator saveCalculator(CalculatorInput calculatorInput) {
//        re
//    t}

}
