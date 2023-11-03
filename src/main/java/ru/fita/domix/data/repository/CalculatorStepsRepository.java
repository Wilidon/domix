package ru.fita.domix.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.data.model.CalculatorSteps;
import ru.fita.domix.data.model.Step;

import java.util.Optional;
import java.util.Set;

public interface CalculatorStepsRepository extends JpaRepository<CalculatorSteps, Long>{
    Set<CalculatorSteps> findAllByCalculatorId(Long calculatorId);

    Set<CalculatorSteps> findAllByStepId(Long stepId);
}
