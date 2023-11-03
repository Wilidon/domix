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
import ru.fita.domix.domain.calculator.dto.CalculatorAccess;
import ru.fita.domix.domain.calculator.dto.CalculatorInput;
import ru.fita.domix.domain.calculator.dto.CalculatorRename;

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
    public ResponseEntity<?> getById(@PathVariable long id) {
        return ResponseEntity.ok(calculatorService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(calculatorService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCalculator(@RequestBody CalculatorInput calculatorInput) {
        return ResponseEntity.ok(calculatorService.createCalculator(calculatorInput));
    }

    @Deprecated(since = "0.1.1")
    @PostMapping("/{calculatorId}/steps/{stepId}")
    public ResponseEntity<?> createStep() {
        return ResponseEntity.ok(" ");
    }

    @PatchMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody CalculatorAccess calculatorAccess) { return ResponseEntity.ok(calculatorService.activateCalculator(calculatorAccess));}

    @PatchMapping("/rename")
    public ResponseEntity<?> rename(@RequestBody CalculatorRename calculatorRename) {
        return ResponseEntity.ok(calculatorService.renameCalculator(calculatorRename));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody CalculatorAccess calculatorAccess) {
        calculatorService.deleteCalculator(calculatorAccess);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
