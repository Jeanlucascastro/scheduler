package com.sociedade.scheduler.doman;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends GenericEntity {

    private LocalDateTime initialTime;

    private LocalDateTime finalTime;

    private Type type;

    private Long companyId;

}
