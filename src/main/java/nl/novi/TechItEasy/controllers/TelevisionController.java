package nl.novi.TechItEasy.controllers;

import nl.novi.TechItEasy.dtos.TelevisionOutputDto;
import nl.novi.TechItEasy.dtos.TelevisionInputDto;
import nl.novi.TechItEasy.services.TelevisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {

    private TelevisionService televisionService;

    public TelevisionController(TelevisionService televisionService) {
        this.televisionService = televisionService;
    }

    @GetMapping
    public ResponseEntity<List<TelevisionOutputDto>> getAllTelevisions() {
        return ResponseEntity.ok(televisionService.getTelevisions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelevisionOutputDto> getTelevision(@PathVariable int id) {
        TelevisionOutputDto televisionOutputDto = televisionService.getTelevision(id);
        return ResponseEntity.ok(televisionOutputDto);
    }

    @PostMapping
    public ResponseEntity<String> createTelevision(@Valid @RequestBody TelevisionInputDto televisionInputDto, BindingResult br) {
        if (br.hasErrors()) {
            // something wrong
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {

            int createdId = televisionService.saveTelevision(televisionInputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/televisions/" + createdId).toUriString());
            return ResponseEntity.created(uri).body("Television created!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelevisionOutputDto> updateTelevision(@PathVariable int id, @RequestBody TelevisionInputDto televisionInputDto) {
        TelevisionOutputDto televisionOutputDto = televisionService.updateTelevision(id, televisionInputDto);

        return ResponseEntity.ok(televisionOutputDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTelevision(@RequestParam String name) {
        televisionService.deleteTelevision(name);
        return ResponseEntity.noContent().build();
    }
}
