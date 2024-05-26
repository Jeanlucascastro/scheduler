package com.sociedade.scheduler.controllers;

import com.sociedade.scheduler.model.Schedule;
import com.sociedade.scheduler.model.dto.CreateScheduleDTO;
import com.sociedade.scheduler.model.dto.UpdateScheduleDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public Schedule saveSchedule(@RequestBody CreateScheduleDTO createScheduleDTO, @AuthenticationPrincipal User user) {
        return scheduleService.saveSchedule(createScheduleDTO, user);
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping
    public Page<Schedule> getAllSchedules(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return scheduleService.getAllScheduless(page, size);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleDTO updatedSchedule) {
        return scheduleService.updateSchedule(id, updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

    @GetMapping("/schedule")
    public List<Schedule> findByExecutorIdAndInitialTime(
            @RequestParam Long executorId,
            @RequestParam LocalDate initialTime) {
        return scheduleService.findByExecutorIdAndInitialTime(executorId, initialTime);
    }

    @GetMapping("/by-user")
    public List<Schedule> getScheduleByUser(@AuthenticationPrincipal User user) {
        System.out.println("iuser " + user);
        return scheduleService.findScheduleByUser(user);
    }
}