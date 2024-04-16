package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.dao.StudentAssignmentDAO;
import com.csye6220.eduverse.entity.Grade;
import com.csye6220.eduverse.entity.StudentAssignment;
import com.csye6220.eduverse.pojo.GradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GradeMapper {

    StudentAssignmentDAO studentAssignmentDAO;

    @Autowired
    public GradeMapper(StudentAssignmentDAO studentAssignmentDAO) {
        this.studentAssignmentDAO = studentAssignmentDAO;
    }

    public Grade mapDTOToGrade(GradeDTO gradeDTO) {
        Grade grade = new Grade();
        grade.setComment(gradeDTO.getComment());
        grade.setScore(gradeDTO.getScore());
        StudentAssignment studentAssignment = studentAssignmentDAO.getStudentAssignmentByid(gradeDTO.getStudentAssignmentId());
        grade.setStudentAssignment(studentAssignment);
        return grade;
    }

    public GradeDTO mapGradeToDTO(Grade grade) {
        if(grade == null) {
            return null;
        }
        GradeDTO gradeDTO = new GradeDTO();
        if(Objects.nonNull(grade.getStudentAssignment()) && Objects.nonNull(grade.getStudentAssignment().getAssignment())) {
            gradeDTO.setAssignmentId(grade.getStudentAssignment().getAssignment().getId());
            gradeDTO.setAssignmentName(grade.getStudentAssignment().getAssignment().getName());
            gradeDTO.setStudentAssignmentId(grade.getStudentAssignment().getId());
            gradeDTO.setDueDate(grade.getStudentAssignment().getAssignment().getEndDate());
            gradeDTO.setSubmissionDate(grade.getStudentAssignment().getDateTime());
        }
        if(Objects.nonNull(grade.getStudentAssignment()) && Objects.nonNull(grade.getStudentAssignment().getStudent())) {
            gradeDTO.setStudentId(grade.getStudentAssignment().getStudent().getId());
            gradeDTO.setAssignmentName(grade.getStudentAssignment().getStudent().getUser().getFirstName() + " " + grade.getStudentAssignment().getStudent().getUser().getLastName());
        }
        gradeDTO.setComment(grade.getComment());
        gradeDTO.setScore(grade.getScore());
        return gradeDTO;
    }
}
