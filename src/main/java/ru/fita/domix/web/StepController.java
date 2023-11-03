package ru.fita.domix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.data.model.CalculatorStep;
import ru.fita.domix.domain.step.StepService;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;

@RestController
@RequestMapping("/steps")
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    //Вот это надо вероятно перенести в калькулятор
    @PostMapping("/{calculatorId}/{id}")
    @Deprecated(since = "0.1.1")
    public ResponseEntity<?> insertStep(@PathVariable long calculatorId,
                                        @PathVariable long stepId,
                                        @RequestParam short index){
        CalculatorStep calculatorStep = stepService.insertStep(calculatorId, stepId, index);
        if (calculatorStep == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(calculatorStep, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStep(@PathVariable("id") long stepId) {
        return ResponseEntity.ok(stepService.getStep(stepId));
    }

    //Это надо в калькулятор закинуть
    @PostMapping("")
    public ResponseEntity<OnlyStepOutput> createStep(@RequestBody StepInput stepInput){
        return new ResponseEntity<OnlyStepOutput>(stepService.createStep(stepInput), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> rename(@PathVariable("id") long stepId,
                                    @RequestBody StepName stepName) {
        return ResponseEntity.ok(stepService.renameStep(stepId, stepName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStep(@PathVariable long stepId){
        return ResponseEntity.ok(stepService.deleteStep(stepId));
    }

}
