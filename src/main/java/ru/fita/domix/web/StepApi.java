package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;
import ru.fita.domix.domain.step.dto.StepInput;
import ru.fita.domix.domain.step.dto.StepName;
import ru.fita.domix.domain.step.dto.StepOutput;

import java.util.Set;

@RequestMapping("/steps")
@Tag(name = "Шаги")
public interface StepApi {
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
    ResponseEntity<Set<OnlyStepOutput>> getActiveSteps();

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
    ResponseEntity<StepOutput> getStep(@PathVariable("id") long stepId,
                                              @RequestParam("area") @Min(1) @Max(999) int area,
                                              @RequestParam("floors") @Min(1) @Max(3) int floors);

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
    ResponseEntity<OnlyStepOutput> createStep(@RequestBody StepInput stepInput);

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
    ResponseEntity<OnlyStepOutput> rename(@PathVariable("id") long stepId,
                                                 @RequestBody StepName stepName);

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
    ResponseEntity<?> deleteStep(@PathVariable("id") long stepId);


}
