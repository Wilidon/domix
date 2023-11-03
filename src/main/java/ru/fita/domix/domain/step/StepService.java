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
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;
import ru.fita.domix.domain.step.dto.StepOutput;
import ru.fita.domix.domain.step.exceptions.AlreadyUsingException;
import ru.fita.domix.domain.step.exceptions.NotFoundStepException;
import ru.fita.domix.domain.util.DtoMapper;
import ru.fita.domix.domain.step.StepMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StepService {
    private final CalculatorRepository calculatorRepository;
    private final StepRepository stepRepository;

    private final CalculatorStepsRepository calculatorStepsRepository;

    private final DtoMapper<Step, StepOutput> outputMapper;

    @Autowired
    public StepService(CalculatorRepository calculatorRepository, StepRepository stepRepository,
                       CalculatorStepsRepository calculatorStepsRepository, @Qualifier("stepMapper") DtoMapper<Step, StepOutput> outputMapper) {
        this.calculatorRepository = calculatorRepository;
        this.stepRepository = stepRepository;
        this.calculatorStepsRepository = calculatorStepsRepository;
        this.outputMapper = outputMapper;
    }

    //Создание шага без привязки к калькулятору
    public StepOutput createStep(StepInput stepInput) {
        Step step = new Step();
        step.setTitle(stepInput.getTitle());
        step.setMultipleSelect(stepInput.isMultipleSelect());
        stepRepository.save(step);
        return outputMapper.toDto(step);
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


    public List<StepOutput> getSteps(long calculatorId){
        calculatorRepository.findById(calculatorId).orElseThrow(NotFoundCalculatorException::new);
        Set<CalculatorStep> calculatorSteps =  calculatorStepsRepository.findAllByCalculatorId(calculatorId);
        Set<Step> steps = new HashSet<>();
        for (CalculatorStep calculatorStep : calculatorSteps){
            steps.add(calculatorStep.getStep());
        }
        return  steps.stream().map(outputMapper::toDto).collect(Collectors.toList());
    }

    public StepOutput getStep(long stepId){
        return outputMapper.toDto(stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new));
    }

    public boolean deleteStep(long stepId) {
        Step step = stepRepository.findById(stepId).orElseThrow(NotFoundStepException::new);;
        if(calculatorStepsRepository.findAllByStepId(stepId).isEmpty()){
            stepRepository.delete(step);
            return true;
        }
        throw (new AlreadyUsingException());
    }

    public StepOutput renameStep(StepName stepName){
        Step step = stepRepository.findById(stepName.getId()).orElseThrow(NotFoundStepException::new);;
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
