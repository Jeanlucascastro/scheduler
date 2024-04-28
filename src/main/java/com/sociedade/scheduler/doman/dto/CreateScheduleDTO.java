package com.sociedade.scheduler.doman.dto;

import java.time.LocalDateTime;

public record CreateScheduleDTO(
        LocalDateTime initialTime,
        Long typeId,
        Long companyId,
        Long userId
) {
}
