package com.sociedade.scheduler.doman.dto;

import java.time.LocalDateTime;

public record UpdateScheduleDTO(
        LocalDateTime initialTime,
        Long typeId
) {
}
