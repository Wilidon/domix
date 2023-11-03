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
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;

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

    @Deprecated(since = "0.1.1")
    @PostMapping("/{calculatorId}/steps/{stepId}")
    public ResponseEntity<?> createStep() {
        return ResponseEntity.ok(" ");
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
    public ResponseEntity<?> activate(@PathVariable long id) { return ResponseEntity.ok(calculatorService.activateCalculator(id));}

    @PatchMapping("/{id}/rename")
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
    @DeleteMapping("/{id}/delete")
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
