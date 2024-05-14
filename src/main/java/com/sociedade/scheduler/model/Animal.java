package com.sociedade.scheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sociedade.scheduler.enuns.AnimalType;
import com.sociedade.scheduler.model.user.User;
import jakarta.persistence.*;
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

    @Column
    private String name;

    @Column
    private Long age;

    @Column
    private Long weight;

    @Column
    private Long size;

    @Column
    private AnimalType animalType;

    @Column
    private String note;

    @OneToOne
    @JoinColumn(name = "user_auth_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_yd")
    @JsonIgnore
    private Company company;

}
