package com.sociedade.scheduler.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class Schedule extends GenericEntity {

    private LocalDateTime initialTime;

    private LocalDateTime finalTime;

    private Type type;

    private Long companyId;

    private Long userId;

    private Executor executor;

    public void calculateFinalTime() {
        if (initialTime != null && type != null && type.getTime() != null) {
            this.finalTime = initialTime.plus(type.getTime());
        } else {
            throw new IllegalArgumentException("Initial time or type time cannot be null");
        }
    }
}
