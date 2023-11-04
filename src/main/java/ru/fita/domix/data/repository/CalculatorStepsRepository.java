package ru.fita.domix.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.data.model.Step;

import java.util.Optional;
import java.util.Set;

public interface CalculatorStepsRepository extends JpaRepository<CalculatorStep, Long>{
    Set<CalculatorStep> findAllByCalculatorId(Long calculatorId);

    Optional<CalculatorStep> findByCalculatorAndStep(Calculator calculator,
                                                     Step step);

    Set<CalculatorStep> findAllByStepId(Long stepId);

    void deleteByCalculatorAndStep(Calculator calculator, Step step);

    @Query("select case when count(cs) = 0 " +
            "then 0 else max(cs.order) end " +
            "from CalculatorStep cs where cs.calculator.id = :calculatorId")
    short findMaxOrderByCalculatorId(@Param("calculatorId") long calculatorId);

    @Query("select case when count(cs) > 0" +
            "then true else false " +
            "END " +
            "from CalculatorStep cs " +
            "where cs.calculator.id = :calculatorId " +
            "and cs.step.id =:stepId")
    boolean existsByCalculatorIdAndStepId(@Param("calculatorId") long calculatorId,
                                          @Param("stepId") long stepId);

    @Modifying
    @Query("UPDATE CalculatorStep cs SET cs.order = cast(cs.order - 1 as short ) WHERE cs.order > :deletedOrder")
    void updateOrder(@Param("deletedOrder") short deletedOrder);
}
