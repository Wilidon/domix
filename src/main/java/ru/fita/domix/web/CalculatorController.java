package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.calculator.CalculatorService;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.step.dto.OnlyStepOutput;

import java.util.Set;

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
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(calculatorService.getAll());
    }

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
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok(calculatorService.getById(id));
    }

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
    public ResponseEntity<?> createCalculator(@RequestBody CalculatorInput calculatorInput) {
        return ResponseEntity.ok(calculatorService.createCalculator(calculatorInput));
    }

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
    public ResponseEntity<?> bindStepToCalculator(@PathVariable("calculatorId") long calculatorId,
                                                  @PathVariable("stepId") long stepId) {
        calculatorService.bindStepToCalculator(calculatorId, stepId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
    public ResponseEntity<?> unbindStepFromCalculator(@PathVariable("calculatorId") long calculatorId,
                                                      @PathVariable("stepId") long stepId) {
        calculatorService.unbindStepToCalculator(calculatorId, stepId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/active")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Возвращает калькулятор, который активен в данный момент.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CalculatorOutput.class))
                    })
    })
    public ResponseEntity<CalculatorOutput> getActualCalc() {
        return ResponseEntity.ok(calculatorService.getCalculator());
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<Set<OnlyStepOutput>> getAllSteps(@PathVariable("id") long calculatorId) {
        Set<OnlyStepOutput> onlyStepOutputs = calculatorService.getCalculatorSteps(calculatorId);
        return ResponseEntity.ok(onlyStepOutputs);
    }

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
    public ResponseEntity<?> activate(@PathVariable long id) {
        return ResponseEntity.ok(calculatorService.activateCalculator(id));
    }

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
    public ResponseEntity<?> rename(@PathVariable long id, @RequestBody CalculatorInput calculatorInput) {
        return ResponseEntity.ok(calculatorService.renameCalculator(id, calculatorInput));
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Удаляет калькулятор по указанному в пути ID."
            )
    })
    public ResponseEntity<?> delete(@PathVariable long id) {
        calculatorService.deleteCalculator(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
