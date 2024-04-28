package com.sociedade.scheduler.model.dto;

import com.sociedade.scheduler.enuns.Availability;

public record UpdateExecutorDTO(
        Long userId,
        String name,
        Availability avaliability

) {
}
