package ru.fita.domix.domain.step;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.CalculatorStepsRepository;
import ru.fita.domix.data.repository.StepRepository;
import ru.fita.domix.domain.calculator.CalculatorMapper;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;
import ru.fita.domix.domain.step.dto.StepOutput;
import ru.fita.domix.domain.step.exceptions.AlreadyUsingException;
import ru.fita.domix.domain.step.exceptions.NotFoundStepException;

import java.util.Set;

@Service
@AllArgsConstructor
public class StepService {
    private final CalculatorRepository calculatorRepository;
    private final StepRepository stepRepository;

    private final CalculatorStepsRepository calculatorStepsRepository;

    private final StepMapper stepMapper;

    private final CalculatorMapper calculatorMapper;

    //Создание шага без привязки к калькулятору
    public OnlyStepOutput createStep(StepInput stepInput) {
        Step step = new Step();
        step.setTitle(stepInput.getTitle());
        step.setMultipleSelect(stepInput.isMultipleSelect());
        stepRepository.save(step);
        return stepMapper.mapToOnlyStepOutput(step);
    }


    public StepOutput getStep(long stepId, int area, int floors) {
        Step step = stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new);
        return stepMapper.toDtoWithAreaAndFloors(step, area, floors);
    }

    public void deleteStep(long stepId) {
        Step step = stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new);
        ;
        if (calculatorStepsRepository.findAllByStepId(stepId).isEmpty()) {
            stepRepository.delete(step);
            return;
        }
        throw (new AlreadyUsingException());
    }

    public OnlyStepOutput renameStep(long stepId, StepName stepName) {
        Step step = stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new);
        step.setTitle(stepName.getTitle());
        stepRepository.save(step);
        return stepMapper.mapToOnlyStepOutput(step);
    }

    public void detachSteps(long calculatorId) {
        Set<CalculatorStep> calculatorSteps = calculatorStepsRepository.findAllByCalculatorId(calculatorId);
        calculatorSteps.forEach(x -> x.setCalculator(null));
        calculatorStepsRepository.saveAll(calculatorSteps);
    }

    public Set<OnlyStepOutput> getActiveSteps() {
        Calculator calculator = calculatorRepository.findByStatus(CalculatorStatus.ACTIVE)
                .orElseThrow(NotFoundCalculatorException::new);
        return calculatorMapper.mapStepToOnlyStepList(calculator.getCalculatorSteps());
    }
}
