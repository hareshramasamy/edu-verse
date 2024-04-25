package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.*;
import com.csye6220.eduverse.entity.*;
import com.csye6220.eduverse.mapper.GradeMapper;
import com.csye6220.eduverse.pojo.AssignmentDTO;
import com.csye6220.eduverse.pojo.GradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Objects;

@Service
public class GradeServiceImpl implements GradeService {

    GradeMapper gradeMapper;

    GradeDAO gradeDAO;

    StudentAssignmentDAO studentAssignmentDAO;

    @Autowired
    public GradeServiceImpl(GradeMapper gradeMapper, GradeDAO gradeDAO, StudentAssignmentDAO studentAssignmentDAO) {
        this.gradeMapper = gradeMapper;
        this.gradeDAO = gradeDAO;
        this.studentAssignmentDAO = studentAssignmentDAO;
    }

    @Override
    public void gradeSubmission(GradeDTO gradeDTO) {
//        Assignment assignment = studentAssignmentDAO.getStudentAssignmentByid(gradeDTO.getStudentAssignmentId()).getAssignment();
//        List<StudentAssignment> studentAssignments = studentAssignmentDAO.getStudentAssignmentsByAssignmentId(assignment.getId());
//        for(StudentAssignment studentAssignment: studentAssignments) {
//            Grade grade = new Grade();
//            grade.setScore(gradeDTO.getScore());
//            grade.setComment(gradeDTO.getComment());
//            grade.setStudentAssignment(studentAssignment);
//            gradeDAO.createSubmission(grade);
//        }
        Grade grade = gradeMapper.mapDTOToGrade(gradeDTO);
        gradeDAO.createSubmission(grade);
    }

    @Override
    public Map<Long, Map<Long, GradeDTO>> retrieveStudentsAndGradesMap(List<Student> students, List<AssignmentDTO> assignments) {
        Map<Long, Map<Long,GradeDTO>> studentGradeMap = new HashMap<>();
        for(Student student: students) {
            Map<Long, GradeDTO> assignmentGradesMap = new HashMap<>();
            for(AssignmentDTO assignmentDTO : assignments) {
                GradeDTO gradeDTO = gradeMapper.mapGradeToDTO(gradeDAO.getGradeByStudentAndAssignment(student.getId(), assignmentDTO.getId()));
                if(gradeDTO != null) {
                    assignmentGradesMap.put(assignmentDTO.getId(), gradeDTO);
                }
            }
            studentGradeMap.put(student.getId(), assignmentGradesMap);
        }
        return studentGradeMap;
    }

    @Override
    public Map<Long, GradeDTO> retrieveGradeMapForCurrentUser(List<AssignmentDTO> assignments, String name) {
        Map<Long, GradeDTO> assignmentGradesMap = new HashMap<>();
        for(AssignmentDTO assignmentDTO : assignments) {
            GradeDTO gradeDTO = gradeMapper.mapGradeToDTO(gradeDAO.getGradeByStudentNameAndAssignment(name, assignmentDTO.getId()));
            if(gradeDTO != null) {
                assignmentGradesMap.put(assignmentDTO.getId(), gradeDTO);
            }
        }
        return assignmentGradesMap;
    }
}
