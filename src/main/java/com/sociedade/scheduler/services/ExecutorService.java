package com.sociedade.scheduler.services;

import com.sociedade.scheduler.model.Executor;
import com.sociedade.scheduler.model.dto.CreateExecutorDTO;
import com.sociedade.scheduler.model.dto.UpdateExecutorDTO;
import com.sociedade.scheduler.repositories.ExecutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutorService {

    @Autowired
    private ExecutorRepository executorRepository;

    public Executor saveExecutor(CreateExecutorDTO createExecutorDTO) {

        Executor executor = new Executor(
                createExecutorDTO.userId(),
                createExecutorDTO.name(),
                createExecutorDTO.avaliability(),
                createExecutorDTO.companyId()
        );

        return this.executorRepository.save(executor);
    }

    public Executor getExecutorById(Long id) {
        return this.executorRepository.findById(id).orElse(null);
    }

    public Page<Executor> getAllExecutors(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return executorRepository.findAll(pageRequest);
    }

    public List<Executor> getAllExecutors() {
        return executorRepository.findAll();
    }

    public Executor updateExecutor(Long id, UpdateExecutorDTO updatedExecutor) {
        Executor existingExecutor = this.executorRepository.findById(id).orElse(null);
        if (existingExecutor != null) {
            existingExecutor.setName(updatedExecutor.name());
            existingExecutor.setId(updatedExecutor.userId());
            existingExecutor.setAvailability(updatedExecutor.avaliability());
            return this.executorRepository.save(existingExecutor);
        }
        return null;
    }

    public void deleteExecutor(Long id) {
        this.executorRepository.deleteById(id);
    }
}
