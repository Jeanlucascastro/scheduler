package com.sociedade.scheduler.model;

import com.sociedade.scheduler.enuns.Availability;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Executor extends GenericEntity {

    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Availability availability;

}
