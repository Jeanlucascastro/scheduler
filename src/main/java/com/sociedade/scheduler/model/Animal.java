package com.sociedade.scheduler.model;

import com.sociedade.scheduler.enuns.AnimalType;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class Animal extends GenericEntity {

    private String name;

    private Long age;

    private Long weight;

    private Long size;

    private AnimalType animalType;

    private String note;

}
