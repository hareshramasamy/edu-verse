package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.AssignmentDAO;
import com.csye6220.eduverse.dao.GradeDAO;
import com.csye6220.eduverse.dao.StudentAssignmentDAO;
import com.csye6220.eduverse.entity.Assignment;
import com.csye6220.eduverse.entity.Grade;
import com.csye6220.eduverse.entity.StudentAssignment;
import com.csye6220.eduverse.mapper.AssignmentMapper;
import com.csye6220.eduverse.pojo.AssignmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentsServiceImpl implements AssignmentsService {

    private final AssignmentDAO assignmentDAO;
    private final StudentAssignmentDAO studentAssignmentDAO;
    private final GradeDAO gradeDAO;
    private final AssignmentMapper assignmentMapper;

    @Autowired
    public AssignmentsServiceImpl(AssignmentDAO assignmentDAO, AssignmentMapper assignmentMapper, StudentAssignmentDAO studentAssignmentDAO, GradeDAO gradeDAO) {
        this.assignmentDAO = assignmentDAO;
        this.assignmentMapper = assignmentMapper;
        this.studentAssignmentDAO = studentAssignmentDAO;
        this.gradeDAO = gradeDAO;
    }

    @Override
    public List<AssignmentDTO> getAssignmentsByCourseOfferingId(Long courseOfferingId) {
        return assignmentDAO.getAssignmentsByCourseOfferingId(courseOfferingId)
                .stream()
                .map(assignmentMapper::mapAssignmentsToDTO)
                .toList();
    }

    @Override
    public void createAssignment(AssignmentDTO assignmentDTO, String name) {
        Assignment assignment = assignmentMapper.mapDTOToAssignment(assignmentDTO, name);
        assignmentDAO.createAssignment(assignment);
    }

    @Override
    public AssignmentDTO getAssignmentById(Long assignmentId) {
        return assignmentMapper.mapAssignmentToDtoSinglePage(assignmentDAO.getAssignmentById(assignmentId));
    }

    @Override
    public AssignmentDTO getAssignmentForEditPage(Long assignmentId) {
        return assignmentMapper.mapAssignmentsToDTO(assignmentDAO.getAssignmentById(assignmentId));
    }

    @Override
    public AssignmentDTO editAssignmentById(AssignmentDTO assignmentDTO, Long assignmentId, String name) {
        Assignment assignment = assignmentDAO.getAssignmentById(assignmentId);
        assignment.setDescription(assignment.getDescription());
        assignment.setName(assignmentDTO.getName());
        assignment.setStartDate(assignmentDTO.getStartDate());
        assignment.setEndDate(assignmentDTO.getEndDate());
        return assignmentMapper.mapAssignmentToDtoSinglePage(assignmentDAO.editAssignment(assignment));
    }

    @Override
    public void deleteAssignmentById(Long assignmentId) {
        Assignment assignment = assignmentDAO.getAssignmentById(assignmentId);
        List<StudentAssignment> studentAssignments = studentAssignmentDAO.getStudentAssignmentsByAssignmentId(assignmentId);
        List<Grade> grades = gradeDAO.getGradesByAssignmentId(assignmentId);
        grades.forEach(gradeDAO::deleteGrade);
        studentAssignments.forEach(studentAssignmentDAO::deleteStudentAssignment);
        assignmentDAO.deleteAssignment(assignment);
    }
}
