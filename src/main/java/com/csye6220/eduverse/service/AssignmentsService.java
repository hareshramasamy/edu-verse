package com.csye6220.eduverse.service;

import com.csye6220.eduverse.pojo.AnnouncementDTO;
import com.csye6220.eduverse.pojo.AssignmentDTO;

import java.util.List;

public interface AssignmentsService {
    List<AssignmentDTO> getAssignmentsByCourseOfferingId(Long courseOfferingId);

    void createAssignment(AssignmentDTO assignmentDTO, String name);

    AssignmentDTO getAssignmentById(Long assignmentId);
    AssignmentDTO getAssignmentForEditPage(Long assignmentId);

    AssignmentDTO editAssignmentById(AssignmentDTO assignmentDTO, Long assignmentId, String name);

    void deleteAssignmentById(Long assignmentiD);
}
