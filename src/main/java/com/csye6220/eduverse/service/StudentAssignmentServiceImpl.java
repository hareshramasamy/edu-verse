package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.EnrollmentDAO;
import com.csye6220.eduverse.dao.StudentAssignmentDAO;
import com.csye6220.eduverse.dao.StudentDAO;
import com.csye6220.eduverse.entity.Student;
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

    EnrollmentDAO enrollmentDAO;

    public StudentAssignmentServiceImpl(StudentAssignmentMapper studentAssignmentMapper, StudentAssignmentDAO studentAssignmentDAO, EnrollmentDAO enrollmentDAO) {
        this.studentAssignmentMapper = studentAssignmentMapper;
        this.studentAssignmentDAO = studentAssignmentDAO;
        this.enrollmentDAO = enrollmentDAO;
    }

    @Override
    public void uploadStudentAssignment(StudentAssignmentDTO studentAssignmentDTO, Long assignmentId, String name) throws IOException {
//        List<Student> students = enrollmentDAO.getStudentsByAssignmentId(assignmentId);
//        List<StudentAssignment> studentAssignments = students.stream().map(student -> {
//            try {
//                return studentAssignmentMapper.mapDTOToStudenAssignment(studentAssignmentDTO, assignmentId, student.getUser().getUsername());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).toList();
//        studentAssignments.forEach(studentAssignment -> studentAssignmentDAO.saveStudentAssignment(studentAssignment));
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
