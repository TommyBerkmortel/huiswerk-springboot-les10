package nl.novi.TechItEasy.controllers;

import nl.novi.TechItEasy.Repositories.TelevisionRepository;
import nl.novi.TechItEasy.exceptions.RecordNotFoundException;
import nl.novi.TechItEasy.models.Television;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {
    @Autowired
    TelevisionRepository televisionRepository;

    @GetMapping
    public ResponseEntity<Object> getAllTelevisions() {
        if (televisionRepository.count() > 0) {
            return new ResponseEntity<>(televisionRepository.findAll(), HttpStatus.OK);
        } else {
            throw new RecordNotFoundException("No televisions found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Television> getTelevision(@PathVariable int id) {
        if (televisionRepository.existsById(id)) {
            Optional <Television> optionalTelevision = televisionRepository.findById(id);
            return new ResponseEntity<>(optionalTelevision.get(), HttpStatus.OK);
        } else {
            throw new RecordNotFoundException(id + " not found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createTelevision(@RequestBody Television television) {
            if (televisionRepository.existsByName(television.getName())) {
                throw new RecordNotFoundException("Television is allready in our list!");
            }
        televisionRepository.save(television);
        return new ResponseEntity<>(television, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTelevision(@PathVariable int id, @RequestBody Television television) {
        Optional<Television> updatedTelevision = televisionRepository.findById(id);
        if (updatedTelevision.isPresent()) {
            Television newTelevision = updatedTelevision.get();
            if (television.getType() != null) {
                newTelevision.setType(television.getType());
            }
            if (television.getBrand() != null) {
                newTelevision.setBrand(television.getBrand());
            }
            if (television.getName() != null) {
                newTelevision.setName(television.getName());
            }
            if(television.getPrice() > 0) {
                newTelevision.setPrice(television.getPrice());
            }
            if(television.getAvailableSize() > 0) {
                newTelevision.setAvailableSize(television.getAvailableSize());
            }
            if(television.getRefreshRate() > 0) {
                newTelevision.setRefreshRate(television.getRefreshRate());
            }
            if (television.getScreenType() != null) {
                newTelevision.setScreenType(television.getScreenType());
            }
            if (television.getScreenQuality() != null) {
                newTelevision.setScreenQuality(television.getScreenQuality());
            }
            if(television.isSmartTv() != newTelevision.isSmartTv()) {
                newTelevision.setSmartTv(television.isSmartTv());
            }
            if(television.isWifi() != newTelevision.isWifi()) {
                newTelevision.setWifi(television.isWifi());
            }
            if(television.isVoiceControl() != newTelevision.isVoiceControl()) {
                newTelevision.setVoiceControl(television.isVoiceControl());
            }
            if(television.isHdr() != newTelevision.isHdr()) {
                newTelevision.setHdr(television.isHdr());
            }
            if(television.isBluetooth() != newTelevision.isBluetooth()) {
                newTelevision.setBluetooth(television.isBluetooth());
            }
            if(television.isAmbiLight() != newTelevision.isAmbiLight()) {
                newTelevision.setAmbiLight(television.isAmbiLight());
            }
            if(television.getOriginalStock() > 0) {
                newTelevision.setOriginalStock(television.getOriginalStock());
            }
            if(television.getSold() > 0) {
                newTelevision.setSold(television.getSold());
            }

            televisionRepository.save(television);
            return new ResponseEntity<>(television, HttpStatus.NO_CONTENT);
        } else {
            throw  new RecordNotFoundException("The id/body input are invalid");
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteTelevision(@RequestParam String name) {
            Optional <Television> deleteTelevision = televisionRepository.findByName(name);
            if (televisionRepository.existsByName(name)) {
                televisionRepository.delete(deleteTelevision.get());
                return new ResponseEntity<>(name + " is deleted", HttpStatus.OK);
            }
        throw new RecordNotFoundException("No television found!");
    }
}
