package ru.fita.domix.domain.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fita.domix.data.model.Calculator;
import ru.fita.domix.data.model.CalculatorStatus;
import ru.fita.domix.data.repository.CalculatorRepository;
import ru.fita.domix.domain.calculator.dto.CalculatorAccess;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.calculator.dto.CalculatorRename;
import ru.fita.domix.domain.calculator.exceptions.NotFoundCalculatorException;
import ru.fita.domix.domain.step.StepService;
import ru.fita.domix.domain.util.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculatorService {
    private final CalculatorRepository calculatorRepository;
    private final StepService stepService;
    private final DtoMapper<Calculator, CalculatorOutput> outputMapper;
    @Autowired
    public CalculatorService(CalculatorRepository calculatorRepository,
                             StepService stepService,
                             @Qualifier("calculatorMapper") DtoMapper<Calculator, CalculatorOutput> outputMapper) {
        this.calculatorRepository = calculatorRepository;
        this.stepService = stepService;
        this.outputMapper = outputMapper;
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
    public CalculatorOutput activateCalculator(CalculatorAccess calculatorAccess) {
        Calculator calculator = calculatorRepository.findById(calculatorAccess.getId())
                .orElseThrow(NotFoundCalculatorException::new);

        return activateCalculator(calculator);
    }

    @Transactional
    public CalculatorOutput renameCalculator(CalculatorRename calculatorRename) {
        Calculator calculator = calculatorRepository.findById(calculatorRename.getId())
                .orElseThrow(NotFoundCalculatorException::new);
        calculator.setName(calculatorRename.getNewName());
        calculatorRepository.save(calculator);

        return outputMapper.toDto(calculator);
    }

    @Transactional
    public void deleteCalculator(CalculatorAccess calculatorAccess) {
        stepService.detachSteps(calculatorAccess.getId());
        calculatorRepository.deleteById(calculatorAccess.getId());
    }
}
