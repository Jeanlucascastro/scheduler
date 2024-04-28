package com.sociedade.scheduler.controllers;

import com.sociedade.scheduler.model.Executor;
import com.sociedade.scheduler.model.dto.CreateExecutorDTO;
import com.sociedade.scheduler.model.dto.UpdateExecutorDTO;
import com.sociedade.scheduler.services.ExecutorService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/executors")
public class ExecutorController {

    private final ExecutorService executorService;

    public ExecutorController(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @PostMapping
    public ResponseEntity<Executor> createExecutor(@RequestBody CreateExecutorDTO createExecutorDTO) {
        Executor createdExecutor = executorService.saveExecutor(createExecutorDTO);
        return ResponseEntity.ok(createdExecutor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Executor> getExecutorById(@PathVariable Long id) {
        Executor executor = executorService.getExecutorById(id);
        if (executor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(executor);
    }

    @GetMapping
    public ResponseEntity<Page<Executor>> getAllExecutors(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<Executor> allExecutors = executorService.getAllExecutors(page, size);
        return ResponseEntity.ok(allExecutors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Executor> updateExecutor(@PathVariable Long id, @RequestBody UpdateExecutorDTO updateExecutorDTO) {
        Executor updatedExecutor = executorService.updateExecutor(id, updateExecutorDTO);
        if (updatedExecutor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedExecutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExecutor(@PathVariable Long id) {
        executorService.deleteExecutor(id);
        return ResponseEntity.ok().build();
    }
}