package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.calculator.CalculatorService;
import ru.fita.domix.domain.calculator.dto.CalculatorOutput;

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
    public ResponseEntity<CalculatorOutput> getActualCalc() {
        return ResponseEntity.ok(calculatorService.getCalculator());
    }

//    @PostMapping("")
//    public ResponseEntity<?> createCalculator() {
//
//    }

    @Deprecated(since = "0.1.1")
    @PostMapping("/{calculatorId}/steps/{stepId}")
    public ResponseEntity<?> createStep() {
        return ResponseEntity.ok(" ");
    }


}
