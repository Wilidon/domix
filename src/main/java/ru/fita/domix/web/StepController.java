package ru.fita.domix.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.step.StepService;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;
import ru.fita.domix.domain.step.dto.StepOutput;

import java.util.Set;

@RestController

@CrossOrigin(origins = "*")
@Validated
public class StepController implements StepApi {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }


    public ResponseEntity<Set<OnlyStepOutput>> getActiveSteps() {
        return ResponseEntity.ok(stepService.getActiveSteps());
    }

    public ResponseEntity<StepOutput> getStep(@PathVariable("id") long stepId,
                                              @RequestParam("area") @Min(1) @Max(999) int area,
                                              @RequestParam("floors") @Min(1) @Max(3) int floors) {
        return ResponseEntity.ok(stepService.getStep(stepId,
                area, floors));
    }

    public ResponseEntity<OnlyStepOutput> createStep(@RequestBody StepInput stepInput) {
        return new ResponseEntity<>(stepService.createStep(stepInput), HttpStatus.OK);
    }


    public ResponseEntity<OnlyStepOutput> rename(@PathVariable("id") long stepId,
                                                 @RequestBody StepName stepName) {
        return ResponseEntity.ok(stepService.renameStep(stepId, stepName));
    }


    public ResponseEntity<?> deleteStep(@PathVariable("id") long stepId) {
        stepService.deleteStep(stepId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
