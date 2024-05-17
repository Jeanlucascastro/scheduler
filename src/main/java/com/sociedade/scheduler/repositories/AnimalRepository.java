package com.sociedade.scheduler.repositories;

import com.sociedade.scheduler.model.Animal;
import com.sociedade.scheduler.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Page<Animal> findByUser(User user, Pageable pageable);
    List<Animal> findByUser(User user);
}
