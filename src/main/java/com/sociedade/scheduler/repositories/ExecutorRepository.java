package com.sociedade.scheduler.repositories;

import com.sociedade.scheduler.model.Executor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutorRepository extends JpaRepository<Executor, Long> {
}

