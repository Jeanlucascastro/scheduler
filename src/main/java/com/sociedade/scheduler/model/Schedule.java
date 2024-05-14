package com.sociedade.scheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sociedade.scheduler.model.user.User;
import jakarta.persistence.*;
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

    private String animalName;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "company_yd")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Executor executor;

    @OneToOne
    @JoinColumn(name = "user_auth_id")
    @JsonIgnore
    private User user;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column
    private String note;

    public void calculateFinalTime() {
        if (initialTime != null && type != null && type.getTime() != null) {
            this.finalTime = initialTime.plus(type.getTime());
        } else {
            throw new IllegalArgumentException("Initial time or type time cannot be null");
        }
    }

    public void addAnimal(Animal animal) {
        this.animal = animal;
        this.animalName = animal.getName();
    }
}
