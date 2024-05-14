package com.sociedade.scheduler.model.dto;

import com.sociedade.scheduler.enuns.AnimalType;

public record UpdateAnimalDTO(
        String name,
        Long age,
        Long weight,
        Long size,
        AnimalType animalType,
        String note
) {
}
