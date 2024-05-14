package com.sociedade.scheduler.repositories;

import com.sociedade.scheduler.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
