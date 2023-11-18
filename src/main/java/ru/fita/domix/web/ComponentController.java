package ru.fita.domix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.component.ComponentService;
import ru.fita.domix.domain.component.dto.ComponentInput;
import ru.fita.domix.domain.component.dto.ComponentOutput;

@RestController
public class ComponentController implements ComponentApi {

    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {

        this.componentService = componentService;
    }

    public ResponseEntity<ComponentOutput> createComponent(@RequestBody ComponentInput componentInput) {
        return new ResponseEntity<>(componentService.createComponent(componentInput), HttpStatus.OK);
    }

    public ResponseEntity<?> getComponent(@PathVariable("componentId") long componentId) {
        return ResponseEntity.ok(componentService.getComponent(componentId));
    }

    public ResponseEntity<?> deleteComponent(@PathVariable("componentId") long componentId) {
        return ResponseEntity.ok(componentService.deleteComponent(componentId));
    }

}
