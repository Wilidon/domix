package ru.fita.domix.domain.calculator;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.StepRepository;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;

@Service
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final StepRepository stepRepository;

    @Autowired
    public CalculatorService(CalculatorRepository calculatorRepository, StepRepository stepRepository) {
        this.calculatorRepository = calculatorRepository;
        this.stepRepository = stepRepository;
    }

    public Calculator getCalculator() {
        return calculatorRepository.findByStatus(CalculatorStatus.ACTIVE)
                .orElseThrow(NotFoundCalculatorException::new);
    }

//    public Calculator saveCalculator(CalculatorInput calculatorInput) {
//        re
//    t}

}
