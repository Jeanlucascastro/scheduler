package com.sociedade.scheduler.services;

import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.Executor;
import com.sociedade.scheduler.model.dto.CreateExecutorDTO;
import com.sociedade.scheduler.model.dto.UpdateExecutorDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.repositories.ExecutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutorService {

    @Autowired
    private ExecutorRepository executorRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private CompanyService companyService;

    public Executor saveExecutor(CreateExecutorDTO createExecutorDTO) {

        Company company = this.companyService.findById(createExecutorDTO.companyId());

        Executor executor = new Executor(
                null,
                createExecutorDTO.name(),
                null,
                company
        );

        if (createExecutorDTO.userId() != null) {
            Optional<User> user = this.authorizationService.findById(createExecutorDTO.userId());
            user.ifPresent(executor::setUser);
        }


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

            if (updatedExecutor.userId() != null) {
                Optional<User> user = this.authorizationService.findById(updatedExecutor.userId());
                user.ifPresent(existingExecutor::setUser);
            } else {
                existingExecutor.setUser(null);
            }

            return this.executorRepository.save(existingExecutor);
        }
        return null;
    }

    public void deleteExecutor(Long id) {
        this.executorRepository.deleteById(id);
    }
}
