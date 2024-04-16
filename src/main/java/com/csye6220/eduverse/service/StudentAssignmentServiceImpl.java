package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.StudentAssignmentDAO;
import com.csye6220.eduverse.entity.StudentAssignment;
import com.csye6220.eduverse.mapper.StudentAssignmentMapper;
import com.csye6220.eduverse.pojo.StudentAssignmentDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StudentAssignmentServiceImpl implements StudentAssignmentService {

    StudentAssignmentMapper studentAssignmentMapper;

    StudentAssignmentDAO studentAssignmentDAO;

    public StudentAssignmentServiceImpl(StudentAssignmentMapper studentAssignmentMapper, StudentAssignmentDAO studentAssignmentDAO) {
        this.studentAssignmentMapper = studentAssignmentMapper;
        this.studentAssignmentDAO = studentAssignmentDAO;
    }

    @Override
    public void uploadStudentAssignment(StudentAssignmentDTO studentAssignmentDTO, Long assignmentId, String name) throws IOException {
        StudentAssignment studentAssignment = studentAssignmentMapper.mapDTOToStudenAssignment(studentAssignmentDTO, assignmentId, name);
        studentAssignmentDAO.saveStudentAssignment(studentAssignment);
    }

    @Override
    public StudentAssignment getStudentAssignmentById(Long studentAssignmentId) {
        return studentAssignmentDAO.getStudentAssignmentByid(studentAssignmentId);
    }

    @Override
    public StudentAssignmentDTO getStudentAssignmentByStudentAndAssignment(Long assignmentId, String name) {
        return studentAssignmentMapper.mapStudenAssignmentToDTO(studentAssignmentDAO.getStudentAssignmentByStudentAndAssignment(assignmentId, name));
    }

    @Override
    public List<StudentAssignmentDTO> getStudentAssignmentsByAssignmentId(Long assignmentId) {
        return studentAssignmentDAO.getStudentAssignmentsByAssignmentId(assignmentId)
                .stream()
                .map(studentAssignment -> studentAssignmentMapper.mapStudenAssignmentToDTO(studentAssignment))
                .toList();
    }

    @Override
    public StudentAssignmentDTO getStudentAssignmentDTOById(Long studentAssignmentId) {
        return studentAssignmentMapper.mapStudenAssignmentToDTO(getStudentAssignmentById(studentAssignmentId));
    }
}
