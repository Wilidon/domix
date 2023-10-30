package ru.fita.domix.domain.step;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.StepRepository;

@Service
@AllArgsConstructor
public class StepService {
    private final CalculatorRepository calculatorRepository;
    private final StepRepository stepRepository;


    public void createStep() {

    }
}
