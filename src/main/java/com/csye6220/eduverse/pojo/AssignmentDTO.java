package com.csye6220.eduverse.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private Long courseOfferingId;

}
