package com.sociedade.scheduler.controllers;

import com.sociedade.scheduler.doman.Schedule;
import com.sociedade.scheduler.doman.dto.CreateScheduleDTO;
import com.sociedade.scheduler.doman.dto.UpdateScheduleDTO;
import com.sociedade.scheduler.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @PostMapping
    public Schedule saveSchedule(@RequestBody CreateScheduleDTO createScheduleDTO) {
        return scheduleService.saveSchedule(createScheduleDTO);
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
}