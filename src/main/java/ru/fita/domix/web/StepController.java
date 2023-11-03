package ru.fita.domix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.domain.step.StepService;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;

@RestController
@RequestMapping("/steps")
@Deprecated(since = "0.1.1")
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/{calculatorId}/allSteps")
    public ResponseEntity<?> getSteps(@PathVariable long calculatorId) {
        return ResponseEntity.ok(stepService.getSteps(calculatorId));
    }
    //Вот это надо вероятно перенести в калькулятор
    @PostMapping("/{calculatorId}/{stepId}")
    public ResponseEntity<?> insertStep(@PathVariable long calculatorId,
                                        @PathVariable long stepId,
                                        @RequestParam short index){
        CalculatorStep calculatorStep = stepService.insertStep(calculatorId, stepId, index);
        if (calculatorStep == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(calculatorStep, HttpStatus.OK);
    }
    //Это надо в калькулятор закинуть
    @PostMapping("")
    public ResponseEntity<?> createStep(@RequestBody StepInput stepInput){
        return new ResponseEntity<>(stepService.createStep(stepInput), HttpStatus.OK);
    }



    @DeleteMapping("/{stepId}")
    public ResponseEntity<?> deleteStep(@PathVariable long stepId){
        return ResponseEntity.ok(stepService.deleteStep(stepId));
    }

    @GetMapping("/{stepId}")
    public ResponseEntity<?> getStep(@PathVariable long stepId) {
        return ResponseEntity.ok(stepService.getStep(stepId));
    }

    @PatchMapping("/{stepId}/rename")
    public ResponseEntity<?> rename(@RequestBody StepName stepName) {
        return ResponseEntity.ok(stepService.renameStep(stepName));
    }
}
