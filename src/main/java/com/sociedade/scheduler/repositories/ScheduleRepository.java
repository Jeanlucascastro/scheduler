package com.sociedade.scheduler.repositories;

import com.sociedade.scheduler.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}

