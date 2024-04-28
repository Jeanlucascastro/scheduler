package com.sociedade.scheduler.controllers;

import com.sociedade.scheduler.model.Type;
import com.sociedade.scheduler.model.dto.CreateTypeDTO;
import com.sociedade.scheduler.model.dto.UpdateTypeDTO;
import com.sociedade.scheduler.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping()
    public ResponseEntity<Type> createType(@RequestBody CreateTypeDTO typeDTO) {
        Type createdType = typeService.saveType(typeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable("id") Long id) {
        Type type = typeService.getTypeById(id);
        if (type != null) {
            return ResponseEntity.ok(type);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTypes(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "paginated", defaultValue = "true") boolean paginated
    ) {
        if (paginated) {
            Page<Type> typesPage = typeService.getAllTypes(page, size);
            return ResponseEntity.ok(typesPage);
        } else {
            return ResponseEntity.ok(typeService.getAllTypes());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> updateType(
            @PathVariable("id") Long id,
            @RequestBody UpdateTypeDTO updatedType
    ) {
        Type updated = typeService.updateType(id, updatedType);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable("id") Long id) {
        typeService.deleteType(id);
        return ResponseEntity.noContent().build();
    }
}
