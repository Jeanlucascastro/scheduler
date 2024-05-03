package com.sociedade.scheduler.services;

import com.sociedade.scheduler.model.Schedule;
import com.sociedade.scheduler.model.Type;
import com.sociedade.scheduler.model.dto.CreateScheduleDTO;
import com.sociedade.scheduler.model.dto.UpdateScheduleDTO;
import com.sociedade.scheduler.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TypeService typeService;

    public Boolean checkAvailability(LocalDateTime initialTime, Type type) {
        LocalDateTime finalTime = initialTime.plus(type.getTime());
        List<Schedule> schedules = scheduleRepository.findOverlappingSchedules(initialTime, finalTime);
        return schedules.isEmpty();
    }

    public Schedule saveSchedule(CreateScheduleDTO createScheduleDTO) {

        Type type = this.typeService.getTypeById(createScheduleDTO.typeId());

        Schedule schedule = new Schedule(
                createScheduleDTO.initialTime(),
                null,
                type,
                createScheduleDTO.companyId(),
                createScheduleDTO.userId(),
                null
        );
        schedule.calculateFinalTime();
        return this.scheduleRepository.save(schedule);
    }

    public Schedule getScheduleById(Long id) {
        return this.scheduleRepository.findById(id).orElse(null);
    }

    public Page<Schedule> getAllScheduless(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return scheduleRepository.findAll(pageRequest);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule updateSchedule(Long id, UpdateScheduleDTO updatedSchedule) {
        Schedule existingSchedule = this.scheduleRepository.findById(id).orElse(null);
        if (existingSchedule != null) {
            existingSchedule.setInitialTime(updatedSchedule.initialTime());
            Type type = this.typeService.getTypeById(updatedSchedule.typeId());
            existingSchedule.setType(type);
            existingSchedule.calculateFinalTime();
            return this.scheduleRepository.save(existingSchedule);
        }
        return null;
    }

    public void deleteSchedule(Long id) {
        this.scheduleRepository.deleteById(id);
    }


    public List<Schedule> findByExecutorIdAndInitialTime(Long executorId, LocalDate initialTime) {
        return scheduleRepository.findByExecutorIdAndInitialTime(executorId, initialTime);
    }

}
