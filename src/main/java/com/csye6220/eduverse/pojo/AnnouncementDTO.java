package com.csye6220.eduverse.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AnnouncementDTO {

    private String title;

    private String content;

    private LocalDateTime dateTime;

    private Long instructorId;

    private Long courseOfferingId;
}
