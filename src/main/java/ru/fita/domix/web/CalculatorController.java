package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.calculator.CalculatorService;

@RestController
@RequestMapping("/calculators")
@Tag(name = "Калькулятор")
@CrossOrigin(origins = "*")
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
