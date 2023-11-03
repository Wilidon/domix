package ru.fita.domix.domain.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.CalculatorStepsRepository;
import ru.fita.domix.data.repository.StepRepository;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;
import ru.fita.domix.domain.step.exceptions.AlreadyUsingException;
import ru.fita.domix.domain.step.exceptions.NotFoundStepException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class StepService {
    private final CalculatorRepository calculatorRepository;
    private final StepRepository stepRepository;

    private final CalculatorStepsRepository calculatorStepsRepository;

    @Autowired
    public StepService(CalculatorRepository calculatorRepository, StepRepository stepRepository,
                       CalculatorStepsRepository calculatorStepsRepository) {
        this.calculatorRepository = calculatorRepository;
        this.stepRepository = stepRepository;
        this.calculatorStepsRepository = calculatorStepsRepository;
    }

    //Создание шага без привязки к калькулятору
    public Step createStep(String title, boolean multipleSelect) {
        Step step = new Step();
        step.setTitle(title);
        step.setMultipleSelect(multipleSelect);
        stepRepository.save(step);
        return step;
    }

    public CalculatorStep insertStep(long calculator_id, long step_id, short index){
        Calculator calculator = calculatorRepository.findById(calculator_id).orElseThrow(NotFoundCalculatorException::new);
        Step step = stepRepository.findById(step_id).orElseThrow(NotFoundStepException::new);;
        if (calculator.getCalculatorSteps().size() < index){
            return null;
        }
        Set<CalculatorStep> stepsBigger = new HashSet<>();
        for(CalculatorStep stepAfterIndex : calculator.getCalculatorSteps()){
            if(stepAfterIndex.getOrder() >= index){
                stepsBigger.add(stepAfterIndex);
            }
        }
        for(CalculatorStep calculatorStep : stepsBigger){
            calculatorStep.setOrder((short) (calculatorStep.getOrder()+1));
            calculatorStepsRepository.save(calculatorStep);
        }
        CalculatorStep calculatorStep = new CalculatorStep();
        calculatorStep.setCalculator(calculator);
        calculatorStep.setStep(step);
        calculatorStep.setOrder(index);
        calculatorStepsRepository.save(calculatorStep);
        return calculatorStep;
    }


    public Set<CalculatorStep> getSteps(long calculatorId){
        Optional<Calculator> optionalCalculator = calculatorRepository.findById(calculatorId);
        if (optionalCalculator.isEmpty()){
            return null;
        }
        return calculatorStepsRepository.findAllByCalculatorId(calculatorId);
    }

    public boolean deleteStep(long stepId) {
        Step step = stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new);;
        if(calculatorStepsRepository.findAllByStepId(stepId).isEmpty()){
            stepRepository.delete(step);
            return true;
        }
        throw (new AlreadyUsingException());
    }

    public void detachSteps(long calculatorId) {
        Set<CalculatorStep> calculatorSteps = calculatorStepsRepository.findAllByCalculatorId(calculatorId);
        calculatorSteps.forEach(x -> x.setCalculator(null));
        calculatorStepsRepository.saveAll(calculatorSteps);
    }
}
