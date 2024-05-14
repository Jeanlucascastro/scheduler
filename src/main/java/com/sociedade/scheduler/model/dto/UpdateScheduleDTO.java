package com.sociedade.scheduler.model.dto;

import java.time.LocalDateTime;

public record UpdateScheduleDTO(
        LocalDateTime initialTime,
        Long typeId,
        String note
) {
}
