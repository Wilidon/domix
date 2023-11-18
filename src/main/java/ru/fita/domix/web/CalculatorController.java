package ru.fita.domix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.fita.domix.domain.calculator.CalculatorService;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;

import java.util.Set;


@RestController
@CrossOrigin(origins = "*")
public class CalculatorController implements CalculatorApi {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(calculatorService.getAll());
    }

    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok(calculatorService.getById(id));
    }

    public ResponseEntity<?> createCalculator(@RequestBody CalculatorInput calculatorInput) {
        return ResponseEntity.ok(calculatorService.createCalculator(calculatorInput));
    }


    public ResponseEntity<?> bindStepToCalculator(@PathVariable("calculatorId") long calculatorId,
                                                  @PathVariable("stepId") long stepId) {
        calculatorService.bindStepToCalculator(calculatorId, stepId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> unbindStepFromCalculator(@PathVariable("calculatorId") long calculatorId,
                                                      @PathVariable("stepId") long stepId) {
        calculatorService.unbindStepToCalculator(calculatorId, stepId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<CalculatorOutput> getActualCalc() {
        return ResponseEntity.ok(calculatorService.getCalculator());
    }

    public ResponseEntity<Set<OnlyStepOutput>> getAllSteps(@PathVariable("id") long calculatorId) {
        Set<OnlyStepOutput> onlyStepOutputs = calculatorService.getCalculatorSteps(calculatorId);
        return ResponseEntity.ok(onlyStepOutputs);
    }

    public ResponseEntity<?> activate(@PathVariable long id) {
        return ResponseEntity.ok(calculatorService.activateCalculator(id));
    }

    public ResponseEntity<?> rename(@PathVariable long id, @RequestBody CalculatorInput calculatorInput) {
        return ResponseEntity.ok(calculatorService.renameCalculator(id, calculatorInput));
    }

    public ResponseEntity<?> delete(@PathVariable long id) {
        calculatorService.deleteCalculator(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
