package com.sociedade.scheduler.doman;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type extends GenericEntity {

    private String name;

    private Duration time;

    private Long companyId;

}
