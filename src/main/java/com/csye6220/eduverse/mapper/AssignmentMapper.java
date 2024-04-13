package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.dao.CourseOfferingDAO;
import com.csye6220.eduverse.entity.Assignment;
import com.csye6220.eduverse.entity.CourseOffering;
import com.csye6220.eduverse.pojo.AssignmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssignmentMapper {
    CourseOfferingDAO courseOfferingDAO;

    @Autowired
    public AssignmentMapper(CourseOfferingDAO courseOfferingDAO) {
        this.courseOfferingDAO = courseOfferingDAO;
    }

    public AssignmentDTO mapAssignmentsToDTO(Assignment assignment) {
        if(assignment == null) {
            return null;
        }
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(assignment.getId());
        assignmentDTO.setName(assignment.getName());
        assignmentDTO.setDescription(assignment.getDescription());
        assignmentDTO.setStartDate(assignment.getStartDate());
        assignmentDTO.setEndDate(assignment.getEndDate());
        assignmentDTO.setCourseOfferingId(Objects.nonNull(assignment.getCourseOffering()) ? assignment.getCourseOffering().getId() : null);
        return assignmentDTO;
    }

    public AssignmentDTO mapAssignmentToDtoSinglePage(Assignment assignment) {
        if(assignment == null) {
            return null;
        }
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(assignment.getId());
        assignmentDTO.setCourseOfferingId(Objects.nonNull(assignment.getCourseOffering()) ? assignment.getCourseOffering().getId() : null);
        String contentWithLineBreaks = assignment.getDescription().replaceAll("\r\n", "<br>");
        assignmentDTO.setDescription(contentWithLineBreaks);
        assignmentDTO.setName(assignment.getName());
        assignmentDTO.setStartDate(assignment.getStartDate());
        assignmentDTO.setEndDate(assignment.getEndDate());
        return assignmentDTO;
    }

    public Assignment mapDTOToAssignment(AssignmentDTO assignmentDTO, String name) {
        Assignment assignment = new Assignment();
        CourseOffering courseOffering = courseOfferingDAO.getCourseOfferingById(assignmentDTO.getCourseOfferingId());
        assignment.setCourseOffering(courseOffering);
        assignment.setName(assignmentDTO.getName());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setStartDate(assignmentDTO.getStartDate());
        assignment.setEndDate(assignmentDTO.getEndDate());
        return assignment;
    }


}
