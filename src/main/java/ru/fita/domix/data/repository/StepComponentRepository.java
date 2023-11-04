package ru.fita.domix.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.data.model.StepComponent;

import java.util.Set;

public interface StepComponentRepository extends JpaRepository<StepComponent, Long>{
    Set<StepComponent> findAllByStepId(Long stepId);

    Set<StepComponent> findAllByComponentId(Long componentId);
}
