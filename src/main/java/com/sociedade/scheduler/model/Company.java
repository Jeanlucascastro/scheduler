package com.sociedade.scheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sociedade.scheduler.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Table()
@Entity()
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Company extends GenericEntity {

    @Column
    private String name;

    @Column
    private String description;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

}


