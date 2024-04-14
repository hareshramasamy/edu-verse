package com.csye6220.eduverse.service;

import com.csye6220.eduverse.entity.StudentAssignment;
import com.csye6220.eduverse.pojo.StudentAssignmentDTO;

import java.io.IOException;

public interface StudentAssignmentService {
    void uploadStudentAssignment(StudentAssignmentDTO studentAssignmentDTO, Long assignmentId, String name) throws IOException;

    StudentAssignment getStudentAssignmentById(Long studentAssignmentId);

    StudentAssignmentDTO getStudentAssignmentByStudentAndAssignment(Long assignmentId, String name);
}
