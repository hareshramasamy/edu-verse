package com.csye6220.eduverse.service;

import com.csye6220.eduverse.entity.Assignment;
import com.csye6220.eduverse.entity.Student;
import com.csye6220.eduverse.pojo.AssignmentDTO;
import com.csye6220.eduverse.pojo.GradeDTO;

import java.util.List;
import java.util.Map;

public interface GradeService {
    void gradeSubmission(GradeDTO gradeDTO);

    Map<Long, Map<Long, GradeDTO>> retrieveStudentsAndGradesMap(List<Student> enrolledStudents, List<AssignmentDTO> assignments);

    Map<Long, GradeDTO> retrieveGradeMapForCurrentUser(List<AssignmentDTO> assignments, String name);
}
