package ru.fita.domix.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fita.domix.data.model.CalculatorStep;

import java.util.Set;

public interface CalculatorStepsRepository extends JpaRepository<CalculatorStep, Long>{
    Set<CalculatorStep> findAllByCalculatorId(Long calculatorId);

    Set<CalculatorStep> findAllByStepId(Long stepId);
}
