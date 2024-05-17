package com.sociedade.scheduler.controllers;

import com.sociedade.scheduler.model.Animal;
import com.sociedade.scheduler.model.dto.CreateAnimalDTO;
import com.sociedade.scheduler.model.dto.UpdateAnimalDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping()
    public ResponseEntity<Animal> createAnimal(@RequestBody CreateAnimalDTO animalDTO, @AuthenticationPrincipal User user) {
        Animal createdAnimal = animalService.saveAnimal(animalDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable("id") Long id) {
        Animal Animal = animalService.getAnimalById(id);
        if (Animal != null) {
            return ResponseEntity.ok(Animal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAnimals(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "paginated", defaultValue = "true") boolean paginated
    ) {
        if (paginated) {
            Page<Animal> AnimalsPage = animalService.getAllAnimals(user, page, size);
            return ResponseEntity.ok(AnimalsPage);
        } else {
            return ResponseEntity.ok(animalService.getAllAnimals(user));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(
            @PathVariable("id") Long id,
            @RequestBody UpdateAnimalDTO updatedAnimal
    ) {
        Animal updated = animalService.updateAnimal(id, updatedAnimal);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("id") Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
