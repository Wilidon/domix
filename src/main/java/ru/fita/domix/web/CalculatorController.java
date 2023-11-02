package ru.fita.domix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fita.domix.domain.calculator.CalculatorService;

@RestController
@RequestMapping("/calculators")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("")
    public ResponseEntity<?> getActualCalc() {

        return ResponseEntity.ok(calculatorService.getCalculator());
    }

    @PostMapping("/{calculatorId}/steps/{stepId}")
    public ResponseEntity<?> createStep() {

        return ResponseEntity.ok(" ");
    }
}
