package ru.fita.domix.domain.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.data.model.Step;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.data.repository.CalculatorStepsRepository;
import ru.fita.domix.data.repository.StepRepository;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;
import ru.fita.domix.domain.exceptions.AlreadyBindedException;
import ru.fita.domix.domain.exceptions.NotBindedException;
import ru.fita.domix.domain.step.StepService;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.util.DtoMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final CalculatorStepsRepository calculatorStepsRepository;
    private final StepRepository stepRepository;
    private final StepService stepService;
    private final DtoMapper<Calculator, CalculatorOutput> outputMapper;
    private final CalcMapper calcMapper;

    @Autowired
    public CalculatorService(CalculatorRepository calculatorRepository,
                             CalculatorStepsRepository calculatorStepsRepository, StepRepository stepRepository, StepService stepService,
                             @Qualifier("calculatorMapper") DtoMapper<Calculator, CalculatorOutput> outputMapper,
                             CalcMapper calcMapper) {
        this.calculatorRepository = calculatorRepository;
        this.calculatorStepsRepository = calculatorStepsRepository;
        this.stepRepository = stepRepository;
        this.stepService = stepService;
        this.outputMapper = outputMapper;
        this.calcMapper = calcMapper;
    }

    public CalculatorOutput getCalculator() {
        return outputMapper.toDto(calculatorRepository.findByStatus(CalculatorStatus.ACTIVE)
                .orElseThrow(NotFoundCalculatorException::new));
    }

    public CalculatorOutput getById(long id) {
        return outputMapper.toDto(calculatorRepository.findById(id)
                .orElseThrow(NotFoundCalculatorException::new));
    }

    public List<CalculatorOutput> getAll() {
        return calculatorRepository.findAll().stream().map(outputMapper::toDto).collect(Collectors.toList());
    }

    public Set<OnlyStepOutput> getCalculatorSteps(long calculatorId) {
        Calculator calculator = calculatorRepository.findById(calculatorId).orElseThrow(NotFoundCalculatorException::new);
        return calcMapper.mapStepToOnlyStepList(calculator.getCalculatorSteps());
    }

    @Transactional
    public CalculatorOutput createCalculator(CalculatorInput calculatorInput) {
        Calculator calculator = new Calculator();
        calculator.setName(calculatorInput.getName());
        if (!calculatorRepository.existsByStatus(CalculatorStatus.ACTIVE)) {
            calculator.setStatus(CalculatorStatus.ACTIVE);
        }

        calculatorRepository.save(calculator);

        return outputMapper.toDto(calculator);
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

        return outputMapper.toDto(calculator);

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

        return outputMapper.toDto(calculator);
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
