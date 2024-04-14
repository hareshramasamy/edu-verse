package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.entity.StudentAssignment;
import com.csye6220.eduverse.pojo.StudentAssignmentDTO;
import com.csye6220.eduverse.pojo.UserDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.CourseOfferingService;
import com.csye6220.eduverse.service.StudentAssignmentService;
import com.csye6220.eduverse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Objects;

@Controller
public class StudentAssignmentController {

    CourseOfferingService courseOfferingService;

    StudentAssignmentService studentAssignmentService;

    SecurityUtil securityUtil;

    UserService userService;

    @Autowired
    public StudentAssignmentController(CourseOfferingService courseOfferingService, SecurityUtil securityUtil, UserService userService, StudentAssignmentService studentAssignmentService) {
        this.courseOfferingService = courseOfferingService;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.studentAssignmentService = studentAssignmentService;
    }

    @GetMapping("/courses/{courseOfferingId}/assignments/{assignmentId}/start-assignment")
    public String startAssignmentById(@PathVariable Long courseOfferingId, @PathVariable Long assignmentId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
            model.addAttribute("studentAssignment", new StudentAssignmentDTO());
            model.addAttribute("assignmentId", assignmentId);
            model.addAttribute("courseOfferingId", courseOfferingId);
        }
        return "submit-assignment";
    }

    @PostMapping("/courses/{courseOfferingId}/assignments/{assignmentId}/start-assignment")
    public String uploadStudentAssignment(@PathVariable Long courseOfferingId, @PathVariable Long assignmentId, @Valid @ModelAttribute("studentAssignment") StudentAssignmentDTO studentAssignmentDTO, BindingResult result, Model model) throws IOException {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(result.hasErrors()) {
            model.addAttribute("studentAssignment", studentAssignmentDTO);
            model.addAttribute("assignmentId", assignmentId);
            model.addAttribute("courseOfferingId", courseOfferingId);
            if(Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "submit-assignment";
        }
        if(Objects.nonNull(authentication)) {
            studentAssignmentService.uploadStudentAssignment(studentAssignmentDTO, assignmentId, authentication.getName());
        }
        return "redirect:/courses/" + courseOfferingId + "/assignments/" + assignmentId;
    }

    @GetMapping("/courses/{courseOfferingId}/assignments/download/{studentAssignmentId}")
    public ResponseEntity<Resource> downloadStudentAssignment(@PathVariable Long studentAssignmentId, @PathVariable Long courseOfferingId) {
        if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
            return ResponseEntity.notFound().build();
        }
        if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        StudentAssignment studentAssignment = studentAssignmentService.getStudentAssignmentById(studentAssignmentId);
        if(studentAssignment == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + studentAssignment.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(studentAssignment.getSubmissionData()));
    }
}
