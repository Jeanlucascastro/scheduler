package com.sociedade.scheduler.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class Type extends GenericEntity {

    @Column
    private String name;

    @Column
    private Duration time;

    @ManyToOne
    @JoinColumn(name = "company_yd")
    private Company company;

    @Column
    private Double price;

}
