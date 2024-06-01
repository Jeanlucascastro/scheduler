package com.sociedade.scheduler.services;

import com.sociedade.scheduler.model.*;
import com.sociedade.scheduler.model.dto.CreateScheduleDTO;
import com.sociedade.scheduler.model.dto.UpdateScheduleDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AnimalService animalServic;


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

        Optional<Animal> animal = Optional.ofNullable(this.animalServic.getAnimalById(createScheduleDTO.animalId()));

        animal.ifPresent(schedule::setAnimal);

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

            if (updatedSchedule.animalId() != null) {
                Animal animal = this.animalServic.getAnimalById(updatedSchedule.animalId());
                existingSchedule.setAnimal(animal);
                existingSchedule.setAnimalName(animal.getName());
            }
            return this.scheduleRepository.save(existingSchedule);
        }
        return null;
    }

    public void deleteSchedule(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setDeleted(true);
            scheduleRepository.save(schedule);
        } else {
            throw new EntityNotFoundException("Schedule not found with id " + id);
        }
    }


    public List<Schedule> findByExecutorIdAndInitialTime(Long executorId, LocalDate initialTime) {
        return scheduleRepository.findByExecutorIdAndInitialTime(executorId, initialTime);
    }

    public List<Schedule> findScheduleByUser(User user) {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        System.out.println("todao" + today);
       // return scheduleRepository.findByUserAndInitialTimeAfter(user, today);
        return scheduleRepository.findAll();
    }

    public List<TimeSlot> getAvailableSlots(Long companyId, LocalDate date) {
        LocalDateTime startOfDay = date.atTime(8, 0);
        LocalDateTime endOfDay = date.atTime(18, 0);

        List<Schedule> schedules = scheduleRepository.findByCompanyIdAndInitialTimeBetween(companyId, startOfDay, endOfDay);

        List<TimeSlot> allSlots = generateAllSlots();
        List<TimeSlot> occupiedSlots = schedules.stream()
                .flatMap(schedule -> generateOccupiedSlots(schedule).stream())
                .collect(Collectors.toList());

        allSlots.removeAll(occupiedSlots);

        return allSlots;
    }

    private List<TimeSlot> generateAllSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        LocalTime time = LocalTime.of(8, 0);
        int value = 0;
        while (!time.isAfter(LocalTime.of(18, 0))) {
            slots.add(new TimeSlot(time.toString(), value++));
            time = time.plusMinutes(15);
        }
        return slots;
    }

    private List<TimeSlot> generateOccupiedSlots(Schedule schedule) {
        List<TimeSlot> slots = new ArrayList<>();
        LocalDateTime start = schedule.getInitialTime();
        LocalDateTime end = schedule.getFinalTime();
        while (start.isBefore(end)) {
            slots.add(new TimeSlot(start.toLocalTime().toString(), 0)); // Value is not used in comparison
            start = start.plusMinutes(15);
        }
        return slots;
    }

}
