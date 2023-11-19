package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.calculator.dto.CalculatorVersionOutput;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;

import java.util.Set;

@RequestMapping("/calculators")
@Tag(name = "Калькулятор")
public interface CalculatorApi {

    @GetMapping("")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает все существующие калькуляторы.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    })
    })
    ResponseEntity<?> getAll();

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает калькулятор по полученному ID.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    })
    })
    ResponseEntity<?> getById(@PathVariable long id);

    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Создаёт калькулятор с введённым именем. Возвращает созданный калькулятор.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    })
    })
    ResponseEntity<?> createCalculator(@RequestBody CalculatorInput calculatorInput);

    @PostMapping("/{calculatorId}/steps/{stepId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Привязывает выбранный шаг к калькулятору. ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Если калькулятора или шага не существует",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Это шаг уже привязан к этому калькулятору.",
                    content = {
                            @Content()
                    }
            )
    })
    ResponseEntity<?> bindStepToCalculator(@PathVariable("calculatorId") long calculatorId,
                                                  @PathVariable("stepId") long stepId);

    @DeleteMapping("/{calculatorId}/steps/{stepId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Привязывает выбранный шаг к калькулятору. ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Если калькулятора или шага не существует",
                    content = {
                            @Content()
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Это шаг уже привязан к этому калькулятору.",
                    content = {
                            @Content()
                    }
            )
    })
    ResponseEntity<?> unbindStepFromCalculator(@PathVariable("calculatorId") long calculatorId,
                                                      @PathVariable("stepId") long stepId);

    @GetMapping("/active")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает калькулятор, который активен в данный момент. " +
                            "Одновременно может быть только один активный калькульлятор, " +
                            "который будет выводиться " +
                            "на главной странице сайта. Для смены калькулятора " +
                            "необходимо воспользоваться методом /activate",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Возвращает 404, если не существует ни одного активного калькулятора.",
                    content = {
                            @Content()
                    }
            )
    })
    ResponseEntity<CalculatorOutput> getActualCalc(@RequestParam("area") @Min(1) @Max(999) int area,
                                                   @RequestParam("floors") @Min(1) @Max(3) int floors);

    @GetMapping("/version")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает хэш калькулятора",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorVersionOutput.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Возвращает 404, если не существует ни одного активного калькулятора.",
                    content = {
                            @Content()
                    }
            )
    })
    ResponseEntity<CalculatorVersionOutput> version();

    @GetMapping("/{id}/steps")
    ResponseEntity<Set<OnlyStepOutput>> getAllSteps(@PathVariable("id") long calculatorId);

    @PatchMapping("/{id}/activate")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Активирует калькулятор по указанному в пути ID. Возвращает активированный калькулятор.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    })
    })
    ResponseEntity<?> activate(@PathVariable long id);

    @PatchMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Переименовывает калькулятор введёным именем по указанному ID. Возвращает переименнованный калькулятор.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    })
    })
    ResponseEntity<?> rename(@PathVariable long id, @RequestBody CalculatorInput calculatorInput);

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Удаляет калькулятор по указанному в пути ID."
            )
    })
    ResponseEntity<?> delete(@PathVariable long id);
}
