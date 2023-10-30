package ru.fita.domix.domain.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.StepRepository;

@Service
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final StepRepository stepRepository;

    @Autowired
    public CalculatorService(CalculatorRepository calculatorRepository, StepRepository stepRepository) {
        this.calculatorRepository = calculatorRepository;
        this.stepRepository = stepRepository;
    }

    public Calculator getStep() {
        Calculator calculator = calculatorRepository.findByStatus("ok")
                .orElseThrow(() -> new IllegalArgumentException("Не найдено"));

        return calculator;

    }

}
