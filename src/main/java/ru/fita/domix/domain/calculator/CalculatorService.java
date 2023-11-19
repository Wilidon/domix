package ru.fita.domix.domain.calculator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.CalculatorStepsRepository;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.calculator.dto.CalculatorVersionOutput;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;
import ru.fita.domix.domain.exceptions.AlreadyBindedException;
import ru.fita.domix.domain.exceptions.NotBindedException;
import ru.fita.domix.domain.step.StepService;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final CalculatorStepsRepository calculatorStepsRepository;
    private final StepService stepService;
    private final CalculatorMapper calculatorMapper;


    public CalculatorOutput getCalculator(int area, int floors) {
        Calculator calculator = calculatorRepository.findByStatus(CalculatorStatus.ACTIVE)
                .orElseThrow(NotFoundCalculatorException::new);
        return calculatorMapper.mapToDtoWithAreaAndFloors(calculator, area, floors);
    }

    public CalculatorOutput getById(long id) {
        return calculatorMapper.toDto(calculatorRepository.findById(id)
                .orElseThrow(NotFoundCalculatorException::new));
    }

    public List<CalculatorOutput> getAll() {
        return calculatorRepository.findAll().stream().map(calculatorMapper::toDto).collect(Collectors.toList());
    }

    public Set<OnlyStepOutput> getCalculatorSteps(long calculatorId) {
        Calculator calculator = calculatorRepository.findById(calculatorId).orElseThrow(NotFoundCalculatorException::new);
        return calculatorMapper.mapStepToOnlyStepList(calculator.getCalculatorSteps());
    }

    @Transactional
    public CalculatorOutput createCalculator(CalculatorInput calculatorInput) {
        Calculator calculator = new Calculator();
        calculator.setName(calculatorInput.getName());
        if (!calculatorRepository.existsByStatus(CalculatorStatus.ACTIVE)) {
            calculator.setStatus(CalculatorStatus.ACTIVE);
        }

        calculatorRepository.save(calculator);

        return calculatorMapper.toDto(calculator);
    }


    public CalculatorVersionOutput version() {
        Calculator calculator = calculatorRepository.findByStatus(CalculatorStatus.ACTIVE)
                .orElseThrow(NotFoundCalculatorException::new);
        int hashCode = calculator.hashCode();
        String version = Base64.getEncoder().encodeToString(Integer.toString(hashCode).getBytes());
        return new CalculatorVersionOutput(version);

    }

    @Transactional
    public CalculatorOutput activateCalculator(Calculator calculator) {
        Calculator current = calculatorRepository.findByStatus(CalculatorStatus.ACTIVE).orElse(null);
        if (current != null) {
            current.setStatus(CalculatorStatus.DISABLED);
            calculatorRepository.save(current);
        }

        calculator.setStatus(CalculatorStatus.ACTIVE);
        calculatorRepository.save(calculator);

        return calculatorMapper.toDto(calculator);

    }

    @Transactional
    public CalculatorOutput activateCalculator(long id) {
        Calculator calculator = calculatorRepository.findById(id)
                .orElseThrow(NotFoundCalculatorException::new);

        return activateCalculator(calculator);
    }

    @Transactional
    public CalculatorOutput renameCalculator(long id, CalculatorInput calculatorInput) {
        Calculator calculator = calculatorRepository.findById(id)
                .orElseThrow(NotFoundCalculatorException::new);
        calculator.setName(calculatorInput.getName());
        calculatorRepository.save(calculator);

        return calculatorMapper.toDto(calculator);
    }

    @Transactional
    public void deleteCalculator(long id) {
        stepService.detachSteps(id);
        calculatorRepository.deleteById(id);
    }


    private short getLastStepOrder(long calculatorId) {
        return calculatorStepsRepository.findMaxOrderByCalculatorId(calculatorId);
    }

    public void bindStepToCalculator(long calculatorId, long stepId) {
        boolean isBinded = calculatorStepsRepository.existsByCalculatorIdAndStepId(calculatorId, stepId);
        if (isBinded) {
            throw new AlreadyBindedException();
        }
        Calculator calculator = new Calculator();
        calculator.setId(calculatorId);

        Step step = new Step();
        step.setId(stepId);

        short lastStepOrder = getLastStepOrder(calculatorId);

        CalculatorStep calculatorStep = new CalculatorStep();
        calculatorStep.setCalculator(calculator);
        calculatorStep.setStep(step);
        calculatorStep.setOrder((short) (lastStepOrder + 1));
        calculatorStepsRepository.save(calculatorStep);
    }

    @Transactional
    public void unbindStepToCalculator(long calculatorId, long stepId) {
        Calculator calculator = new Calculator();
        calculator.setId(calculatorId);

        Step step = new Step();
        step.setId(stepId);
        CalculatorStep calculatorStep
                = calculatorStepsRepository.findByCalculatorAndStep(calculator, step)
                .orElseThrow(NotBindedException::new);


        calculatorStepsRepository.deleteByCalculatorAndStep(calculator, step);
        calculatorStepsRepository.updateOrder(calculatorStep.getOrder());
    }

}
