package ru.fita.domix.domain.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.CalculatorStepsRepository;
import ru.fita.domix.data.repository.StepRepository;
import ru.fita.domix.domain.calculator.CalcMapper;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;
import ru.fita.domix.domain.step.dto.StepOutput;
import ru.fita.domix.domain.step.exceptions.AlreadyUsingException;
import ru.fita.domix.domain.step.exceptions.NotFoundStepException;
import ru.fita.domix.domain.util.DtoMapper;

import java.util.HashSet;
import java.util.Set;

@Service
public class StepService {
    private final CalculatorRepository calculatorRepository;
    private final StepRepository stepRepository;

    private final CalculatorStepsRepository calculatorStepsRepository;

    private final DtoMapper<Step, StepOutput> outputMapper;

    private final CalcMapper calcMapper;

    @Autowired
    public StepService(CalculatorRepository calculatorRepository, StepRepository stepRepository,
                       CalculatorStepsRepository calculatorStepsRepository, @Qualifier("stepMapper") DtoMapper<Step, StepOutput> outputMapper, CalcMapper calcMapper) {
        this.calculatorRepository = calculatorRepository;
        this.stepRepository = stepRepository;
        this.calculatorStepsRepository = calculatorStepsRepository;
        this.outputMapper = outputMapper;
        this.calcMapper = calcMapper;
    }

    //Создание шага без привязки к калькулятору
    public OnlyStepOutput createStep(StepInput stepInput) {
        Step step = new Step();
        step.setTitle(stepInput.getTitle());
        step.setMultipleSelect(stepInput.isMultipleSelect());
        stepRepository.save(step);
        return calcMapper.mapToOnlyStepOutput(step);
    }

    public CalculatorStep insertStep(long calculator_id, long step_id, short index) {
        Calculator calculator = calculatorRepository.findById(calculator_id).orElseThrow(NotFoundCalculatorException::new);
        Step step = stepRepository.findById(step_id).orElseThrow(NotFoundStepException::new);
        ;
        if (calculator.getCalculatorSteps().size() < index) {
            return null;
        }
        Set<CalculatorStep> stepsBigger = new HashSet<>();
        for (CalculatorStep stepAfterIndex : calculator.getCalculatorSteps()) {
            if (stepAfterIndex.getOrder() >= index) {
                stepsBigger.add(stepAfterIndex);
            }
        }
        for (CalculatorStep calculatorStep : stepsBigger) {
            calculatorStep.setOrder((short) (calculatorStep.getOrder() + 1));
            calculatorStepsRepository.save(calculatorStep);
        }
        CalculatorStep calculatorStep = new CalculatorStep();
        calculatorStep.setCalculator(calculator);
        calculatorStep.setStep(step);
        calculatorStep.setOrder(index);
        calculatorStepsRepository.save(calculatorStep);
        return calculatorStep;
    }




    public StepOutput getStep(long stepId) {
        return outputMapper.toDto(stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new));
    }

    public boolean deleteStep(long stepId) {
        Step step = stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new);
        ;
        if (calculatorStepsRepository.findAllByStepId(stepId).isEmpty()) {
            stepRepository.delete(step);
            return true;
        }
        throw (new AlreadyUsingException());
    }

    public StepOutput renameStep(long stepId, StepName stepName) {
        Step step = stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new);
        step.setTitle(stepName.getTitle());
        stepRepository.save(step);
        return outputMapper.toDto(step);
    }

    public void detachSteps(long calculatorId) {
        Set<CalculatorStep> calculatorSteps = calculatorStepsRepository.findAllByCalculatorId(calculatorId);
        calculatorSteps.forEach(x -> x.setCalculator(null));
        calculatorStepsRepository.saveAll(calculatorSteps);
    }
}
