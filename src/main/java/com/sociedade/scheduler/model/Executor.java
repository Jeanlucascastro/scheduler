package com.sociedade.scheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sociedade.scheduler.enuns.Availability;
import com.sociedade.scheduler.model.user.User;
import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "user_auth_id")
    @JsonIgnore
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    private Availability availability;

    @ManyToOne
    @JoinColumn(name = "company_yd")
    private Company company;


    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.availability = Availability.AVAILABLE;
        } else {
            this.availability = Availability.UNAVAILABLE;
        }
    }

}
