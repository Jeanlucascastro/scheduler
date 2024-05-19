package com.sociedade.scheduler.services;

import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.Schedule;
import com.sociedade.scheduler.model.Type;
import com.sociedade.scheduler.model.dto.CreateScheduleDTO;
import com.sociedade.scheduler.model.dto.UpdateScheduleDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.repositories.ScheduleRepository;
import jakarta.validation.Valid;
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

    @Autowired
    private CompanyService companyService;


    public Boolean checkAvailability(LocalDateTime initialTime, Type type) {
        LocalDateTime finalTime = initialTime.plus(type.getTime());
        List<Schedule> schedules = scheduleRepository.findOverlappingSchedules(initialTime, finalTime);
        return schedules.isEmpty();
    }

    public Schedule saveSchedule(@Valid CreateScheduleDTO createScheduleDTO, User user) {
        System.out.println("user " + user);
        Type type = this.typeService.getTypeById(createScheduleDTO.typeId());

        Company company = this.companyService.findById(createScheduleDTO.companyId());

        Schedule schedule = new Schedule(
                createScheduleDTO.initialTime(),
                null,
                createScheduleDTO.animalName(),
                type,
                company,
                null,
                user,
                null,
                createScheduleDTO.note()
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
            existingSchedule.setNote(updatedSchedule.note());
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

    public List<Schedule> findScheduleByUser(User user) {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return scheduleRepository.findByUserAndInitialTimeAfter(user, today);
    }
}
