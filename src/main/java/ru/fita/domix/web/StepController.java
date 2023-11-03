package ru.fita.domix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.data.model.CalculatorSteps;
import ru.fita.domix.domain.calculator.CalculatorService;
import ru.fita.domix.domain.step.StepService;

@RestController
@RequestMapping("/steps")
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/{calculatorId}")
    public ResponseEntity<?> getSteps(@PathVariable long calculatorId) {
        return ResponseEntity.ok(stepService.getSteps(calculatorId));
    }

    @PostMapping("/{calculatorId}/{stepId}")
    public ResponseEntity<?> insertStep(@PathVariable long calculatorId,@PathVariable long stepId,
                                        @RequestParam short index){
        CalculatorSteps calculatorStep = stepService.insertStep(calculatorId, stepId, index);
        if (calculatorStep == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(calculatorStep, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> insertStep(@RequestParam String title,@RequestParam boolean multipleSelector){
        return new ResponseEntity<>(stepService.createStep(title,multipleSelector), HttpStatus.OK);
    }

    @DeleteMapping("/{stepId}/delete")
    public ResponseEntity<?> deleteStep(@PathVariable long stepId){
        return ResponseEntity.ok(stepService.deleteStep(stepId));
    }
}
