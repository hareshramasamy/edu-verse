package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.dao.AssignmentDAO;
import com.csye6220.eduverse.dao.StudentDAO;
import com.csye6220.eduverse.entity.*;
import com.csye6220.eduverse.pojo.StudentAssignmentDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class StudentAssignmentMapper {

    AssignmentDAO assignmentDAO;

    StudentDAO studentDAO;

    public StudentAssignmentMapper(AssignmentDAO assignmentDAO, StudentDAO studentDAO) {
        this.assignmentDAO = assignmentDAO;
        this.studentDAO = studentDAO;
    }

    public StudentAssignment mapDTOToStudenAssignment(StudentAssignmentDTO studentAssignmentDTO, Long assignmentId, String name) throws IOException {
        StudentAssignment studentAssignment = new StudentAssignment();
        Assignment assignment =  assignmentDAO.getAssignmentById(assignmentId);
        Student student = studentDAO.getStudentByUsername(name);
        studentAssignment.setAssignment(assignment);
        studentAssignment.setStudent(student);
        studentAssignment.setDateTime(LocalDateTime.now());
        studentAssignment.setFileName(studentAssignmentDTO.getSubmissionData().getOriginalFilename());
        studentAssignment.setSubmissionData(studentAssignmentDTO.getSubmissionData().getBytes());
        studentAssignment.setFileType(studentAssignmentDTO.getSubmissionData().getContentType());
        return studentAssignment;
    }

    public StudentAssignmentDTO mapStudenAssignmentToDTO(StudentAssignment studentAssignment) {
        StudentAssignmentDTO studentAssignmentDTO = null;
        if(Objects.nonNull(studentAssignment)) {
            studentAssignmentDTO = new StudentAssignmentDTO();
            studentAssignmentDTO.setId(studentAssignment.getId());
            studentAssignmentDTO.setAssignmentId(Objects.nonNull(studentAssignment.getAssignment()) ? studentAssignment.getAssignment().getId() : null);
            studentAssignmentDTO.setStudentId(Objects.nonNull(studentAssignment.getStudent()) ? studentAssignment.getStudent().getId() : null);
            studentAssignmentDTO.setDateTime(studentAssignment.getDateTime());
            studentAssignmentDTO.setFileName(studentAssignment.getFileName());
            studentAssignmentDTO.setFileType(studentAssignment.getFileType());
        }
        return studentAssignmentDTO;
    }
}
