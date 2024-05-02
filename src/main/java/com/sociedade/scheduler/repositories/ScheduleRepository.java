package com.sociedade.scheduler.repositories;

import com.sociedade.scheduler.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s " +
            "WHERE (:start BETWEEN s.initialTime AND s.finalTime " +
            "OR :end BETWEEN s.initialTime AND s.finalTime) " +
            "OR (s.initialTime BETWEEN :start AND :end " +
            "OR s.finalTime BETWEEN :start AND :end)")
    List<Schedule> findOverlappingSchedules(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);


    List<Schedule> findByExecutorIdAndInitialTime(Long executorId, LocalDate initialTime);

}

