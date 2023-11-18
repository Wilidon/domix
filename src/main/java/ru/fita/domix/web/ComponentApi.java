package ru.fita.domix.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.component.dto.ComponentInput;
import ru.fita.domix.domain.component.dto.ComponentOutput;

@RequestMapping("/components")
@Tag(name = "Компоненты")
public interface ComponentApi {
    @PostMapping("")
    ResponseEntity<ComponentOutput> createComponent(@RequestBody ComponentInput componentInput);

    @GetMapping("/{componentId}")
    ResponseEntity<?> getComponent(@PathVariable("componentId") long componentId);

    @DeleteMapping("/{componentId}")
    ResponseEntity<?> deleteComponent(@PathVariable("componentId") long componentId);
}
