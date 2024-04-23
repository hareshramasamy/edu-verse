package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.AssignmentDTO;
import com.csye6220.eduverse.pojo.StudentAssignmentDTO;
import com.csye6220.eduverse.pojo.UserDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.AssignmentsService;
import com.csye6220.eduverse.service.CourseOfferingService;
import com.csye6220.eduverse.service.StudentAssignmentService;
import com.csye6220.eduverse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class AssignmentsController {

    CourseOfferingService courseOfferingService;
    AssignmentsService assignmentsService;
    SecurityUtil securityUtil;
    UserService userService;
    StudentAssignmentService studentAssignmentService;

    @Autowired
    public AssignmentsController(CourseOfferingService courseOfferingService, AssignmentsService assignmentsService, SecurityUtil securityUtil, UserService userService, StudentAssignmentService studentAssignmentService) {
        this.courseOfferingService = courseOfferingService;
        this.assignmentsService = assignmentsService;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.studentAssignmentService = studentAssignmentService;
    }

    @GetMapping("/courses/{courseOfferingId}/assignments")
    public String getAssignmentsForCourseOffering(@PathVariable Long courseOfferingId, Model model){
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            List<AssignmentDTO> assignments = assignmentsService.getAssignmentsByCourseOfferingId(courseOfferingId);
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("assignments", assignments);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "course-assignments";
    }


    @GetMapping("/courses/{courseOfferingId}/assignments/create")
    public String createAssignmentPage(@PathVariable Long courseOfferingId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
        }
        model.addAttribute("assignment", new AssignmentDTO());
        model.addAttribute("courseOfferingId", courseOfferingId);
        return "create-assignment";
    }

    @PostMapping("/courses/{courseOfferingId}/assignments/create")
    public String createAssignment(@PathVariable Long courseOfferingId, @Valid @ModelAttribute("assignment") AssignmentDTO assignmentDTO, BindingResult result, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(result.hasErrors()) {
            model.addAttribute("assignment", assignmentDTO);
            model.addAttribute("courseOfferingId", courseOfferingId);
            if(Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "create-assignment";
        }
        if(Objects.nonNull(authentication)) {
            assignmentsService.createAssignment(assignmentDTO, authentication.getName());
        }
        return "redirect:/courses/" + courseOfferingId + "/assignments";
    }

    @GetMapping("/courses/{courseOfferingId}/assignments/{assignmentId}")
    public String getAssignmentsById(@PathVariable Long courseOfferingId, @PathVariable Long assignmentId, Model model){
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            AssignmentDTO assignmentDTO = assignmentsService.getAssignmentById(assignmentId);
            if(assignmentDTO == null) {
                return "error/404";
            }
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            StudentAssignmentDTO studentAssignmentDTO = studentAssignmentService.getStudentAssignmentByStudentAndAssignment(assignmentId, authentication.getName());
            if(studentAssignmentDTO == null) {
                model.addAttribute("submissionStatus", "not-started");
            } else {
                model.addAttribute("submissionStatus", "submitted");
            }
            model.addAttribute("studentAssignment", studentAssignmentDTO);
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("assignment", assignmentDTO);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "single-course-assignment";
    }

    @GetMapping("/courses/{courseOfferingId}/assignments/{assignmentId}/edit")
    public String editAssignmentsById(@PathVariable Long courseOfferingId, @PathVariable Long assignmentId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            AssignmentDTO assignment = assignmentsService.getAssignmentForEditPage(assignmentId);
            if(assignment == null) {
                return "error/404";
            }
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
            model.addAttribute("assignment", assignment);
            model.addAttribute("courseOfferingId", courseOfferingId);
        }
        return "edit-assignment";
    }

    @PostMapping("/courses/{courseOfferingId}/assignments/{assignmentId}/edit")
    public String editAssignment(@PathVariable Long courseOfferingId, @PathVariable Long assignmentId, @Valid @ModelAttribute("assignment") AssignmentDTO assignmentDTO, BindingResult result, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(result.hasErrors()) {
            assignmentDTO.setId(assignmentId);
            model.addAttribute("assignment", assignmentDTO);
            model.addAttribute("courseOfferingId", courseOfferingId);
            if(Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "edit-assignment";
        }
        if(Objects.nonNull(authentication)) {
            AssignmentDTO updatedAssignmentDTO = assignmentsService.editAssignmentById(assignmentDTO, assignmentId, authentication.getName());
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("assignment", updatedAssignmentDTO);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));

        }
        model.addAttribute("activeTab", "courses");
        return "redirect:/courses/" + courseOfferingId + "/assignments/" + assignmentId;
    }

    @GetMapping("/courses/{courseOfferingId}/assignments/{assignmentId}/delete")
    public String deleteAssignment(@PathVariable Long courseOfferingId, @PathVariable Long assignmentId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if (Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if (securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            assignmentsService.deleteAssignmentById(assignmentId);
            List<AssignmentDTO> assignments = assignmentsService.getAssignmentsByCourseOfferingId(courseOfferingId);
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("assignments", assignments);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "redirect:/courses/" + courseOfferingId + "/assignments";
    }
}
