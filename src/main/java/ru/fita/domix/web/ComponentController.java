package ru.fita.domix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fita.domix.domain.component.ComponentService;
import ru.fita.domix.domain.component.dto.ComponentInput;
import ru.fita.domix.domain.component.dto.ComponentOutput;

@RestController
@RequestMapping("/components")
public class ComponentController {

    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {

        this.componentService = componentService;
    }

    @PostMapping("")
    public ResponseEntity<ComponentOutput> createComponent(@RequestBody ComponentInput componentInput){
        return new ResponseEntity<ComponentOutput>(componentService.createComponent(componentInput), HttpStatus.OK);
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<?> getComponent(@PathVariable long componentId){
        return ResponseEntity.ok(componentService.getComponent(componentId));
    }

    @DeleteMapping("/{componentId}")
    public ResponseEntity<?> deleteComponent(@PathVariable long componentId){
        return ResponseEntity.ok(componentService.deleteComponent(componentId));
    }

    //Тут что-то новое
}
