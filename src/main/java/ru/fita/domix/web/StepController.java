package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.step.StepService;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;
import ru.fita.domix.domain.step.dto.StepOutput;

import java.util.Set;

@RestController
@RequestMapping("/steps")
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает все шаги активного калькулятора. ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OnlyStepOutput.class)))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Если активного калькулятора на данный момент не существует.",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<Set<OnlyStepOutput>> getActiveSteps() {
        return ResponseEntity.ok(stepService.getActiveSteps());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает шаг по его id вместе с компонентами, " +
                            "входящими в этом шаг.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StepOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Шага с таким id не существует.",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<StepOutput> getStep(@PathVariable("id") long stepId) {
        return ResponseEntity.ok(stepService.getStep(stepId));
    }

    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Создает новый шаг и возвращает его.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OnlyStepOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Шага с таким id не существует",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<OnlyStepOutput> createStep(@RequestBody StepInput stepInput){
        return new ResponseEntity<>(stepService.createStep(stepInput), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Переименовывает имя шага",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OnlyStepOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Шага с таким id не существует",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<OnlyStepOutput> rename(@PathVariable("id") long stepId,
                                    @RequestBody StepName stepName) {
        return ResponseEntity.ok(stepService.renameStep(stepId, stepName));
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Удаляет шаг. " +
                            "При этом шаг не должен быть привязан ни к какому калькулятору.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OnlyStepOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Шага с таким id не существует",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Шага привязан к другим калькулятором. " +
                            "Сначала нужно отвязать, чтобы удалить.",
                    content = {
                            @Content()
                    }
            )
    })
    public ResponseEntity<?> deleteStep(@PathVariable("id") long stepId){
        stepService.deleteStep(stepId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
