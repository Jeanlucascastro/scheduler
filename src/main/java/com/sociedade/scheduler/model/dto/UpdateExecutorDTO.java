package com.sociedade.scheduler.model.dto;

import com.sociedade.scheduler.enuns.Availability;

public record UpdateExecutorDTO(
        String userId,
        String name
) {
}
