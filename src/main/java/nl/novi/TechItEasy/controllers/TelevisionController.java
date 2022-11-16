package nl.novi.TechItEasy.controllers;

import nl.novi.TechItEasy.exceptions.RecordNotFoundException;
import nl.novi.TechItEasy.services.Television;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TelevisionController {
    private ArrayList<Television> televisions;

    public TelevisionController() {
        televisions = new ArrayList<>();

        Television t = new Television();
        t.setName("XboxTV");
        t.setBrand("Microsoft");
        t.setPrice(499.99);
        televisions.add(t);
    }


    @GetMapping("/televisions")
    public ResponseEntity<Object> getAllTelevisions() {
        if (televisions.size() > 0) {
            return new ResponseEntity<>(televisions, HttpStatus.OK);
        } else {
            throw new RecordNotFoundException("No televisions found");
        }
    }

    @GetMapping("/televisions/{id}")
    public ResponseEntity<Object> getTelevision(@PathVariable int id) {
        if (id >= 0 && id < televisions.size()) {
            return new ResponseEntity<>(televisions.get(id), HttpStatus.OK);
        } else {
            throw new RecordNotFoundException("Id not found");
        }
    }

    @PostMapping("/televisions")
    public ResponseEntity<Object> createTelevision(@RequestBody Television television) {
        for (Television t : televisions) {
            if (t.getName().equals(television.getName())) {
                throw new RecordNotFoundException("Television is allready in our list!");
            }
        }
        televisions.add(television);
        return new ResponseEntity<>(television, HttpStatus.OK);
    }

    @PutMapping("/televisions/{id}")
    public ResponseEntity<Object> updateTelevision(@PathVariable int id, @RequestBody Television television) {
        if (id >= 0 && id < televisions.size()) {
            televisions.set(id, television);
            return new ResponseEntity<>(television, HttpStatus.OK);
        } else {
            throw  new RecordNotFoundException("The id/body input are invalid");
        }
    }

    @DeleteMapping("/televisions")
    public ResponseEntity<Object> deleteTelevision(@RequestParam String name) {
        for (int i = 0; i < televisions.size(); i++) {
            if (televisions.get(i).getName().equals(name)) {
                televisions.remove(i);
                return new ResponseEntity<>(name + " is deleted", HttpStatus.OK);
            }
        }
        throw new RecordNotFoundException("No television found!");
    }
}
