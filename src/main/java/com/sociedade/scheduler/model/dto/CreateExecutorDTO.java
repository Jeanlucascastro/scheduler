package com.sociedade.scheduler.model.dto;

import com.sociedade.scheduler.enuns.Availability;

import java.time.LocalDateTime;

public record CreateExecutorDTO(
        Long userId,
        String name,
        Availability avaliability

) {
}
