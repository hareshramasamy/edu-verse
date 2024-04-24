package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.entity.Student;
import com.csye6220.eduverse.pojo.*;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class GradesController {

    CourseOfferingService courseOfferingService;

    SecurityUtil securityUtil;

    AssignmentsService assignmentsService;

    UserService userService;

    StudentAssignmentService studentAssignmentService;

    GradeService gradeService;

    @Autowired
    public GradesController(CourseOfferingService courseOfferingService, SecurityUtil securityUtil, AssignmentsService assignmentsService, UserService userService, GradeService gradeService, StudentAssignmentService studentAssignmentService) {
        this.courseOfferingService = courseOfferingService;
        this.securityUtil = securityUtil;
        this.assignmentsService = assignmentsService;
        this.userService = userService;
        this.gradeService = gradeService;
        this.studentAssignmentService = studentAssignmentService;
    }

    @GetMapping("/courses/{courseOfferingId}/assignments/{assignmentId}/submissions")
    public String getAssignmentSubmissionsForCourseOffering(@PathVariable Long courseOfferingId, @PathVariable Long assignmentId, Model model){
        Authentication authentication = SecurityUtil.getSessionUser();
        if (Objects.nonNull(authentication)) {
            if (!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }

            List<StudentAssignmentDTO> studentAssignments = studentAssignmentService.getStudentAssignmentsByAssignmentId(assignmentId);
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("studentAssignments", studentAssignments);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "assignment-submissions";
    }

    @GetMapping("/courses/{courseOfferingId}/grade/{studentAssignmentId}")
    public String gradeSubmissionByStudentAssignmentId(@PathVariable Long courseOfferingId, @PathVariable Long studentAssignmentId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if (!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if (securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            if (studentAssignmentService.getStudentAssignmentById(studentAssignmentId) == null) {
                return "error/404";
            }
            StudentAssignmentDTO studentAssignmentDTO = studentAssignmentService.getStudentAssignmentDTOById(studentAssignmentId);

            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("studentAssignment", studentAssignmentDTO);
            model.addAttribute("grade", new GradeDTO());
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "grade-assignment";
    }

    @PostMapping("/courses/{courseOfferingId}/grade/{studentAssignmentId}")
    public String submitGradeForStudentAssignment(@PathVariable Long courseOfferingId, @PathVariable Long studentAssignmentId, @Valid @ModelAttribute("grade") GradeDTO gradeDTO, BindingResult result, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        StudentAssignmentDTO studentAssignmentDTO = studentAssignmentService.getStudentAssignmentDTOById(studentAssignmentId);
        if (result.hasErrors()) {
            model.addAttribute("grade", gradeDTO);
            model.addAttribute("studentAssignment", studentAssignmentDTO);
            if (Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "grade-assignment";
        }
        if (Objects.nonNull(authentication)) {
            gradeService.gradeSubmission(gradeDTO);
        }
        return "redirect:/courses/" + courseOfferingId + "/assignments/" + studentAssignmentDTO.getAssignmentId();
    }

    @GetMapping("/courses/{courseOfferingId}/grades")
    public String getAllGrades(@PathVariable Long courseOfferingId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if (Objects.nonNull(authentication)) {
            if (!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if (securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> Objects.equals("ROLE_STUDENT", grantedAuthority.getAuthority()))) {
                List<AssignmentDTO> assignments = assignmentsService.getAssignmentsByCourseOfferingId(courseOfferingId);
                Map<Long, GradeDTO> gradeMap = gradeService.retrieveGradeMapForCurrentUser(assignments, authentication.getName());
                model.addAttribute("assignments", assignments);
                model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
                model.addAttribute("gradesMap", gradeMap);
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
                model.addAttribute("activeTab", "courses");
                return "course-grades-student";
            } else if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> Objects.equals("ROLE_INSTRUCTOR", grantedAuthority.getAuthority()))) {
                List<AssignmentDTO> assignments = assignmentsService.getAssignmentsByCourseOfferingId(courseOfferingId);
                List<Student> enrolledStudents = courseOfferingService.getEnrolledStudents(courseOfferingId, 0);
                Map<Long, Map<Long, GradeDTO>> studentGradesMap =  gradeService.retrieveStudentsAndGradesMap(enrolledStudents, assignments);
                model.addAttribute("assignments", assignments);
                model.addAttribute("students", enrolledStudents);
                model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
                model.addAttribute("studentGradesMap", studentGradesMap);
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
                model.addAttribute("activeTab", "courses");
                return "course-grades-instructor";
            }
        }
        return "error/403";
    }

    @GetMapping("/loadMoreGrades")
    public String loadMoreStudents(@RequestParam int offset, @RequestParam Long courseOfferingId, Model model) {
        List<AssignmentDTO> assignments = assignmentsService.getAssignmentsByCourseOfferingId(courseOfferingId);
        List<Student> additionalStudents = courseOfferingService.getEnrolledStudents(courseOfferingId, offset);
        Map<Long, Map<Long, GradeDTO>> studentGradesMap =  gradeService.retrieveStudentsAndGradesMap(additionalStudents, assignments);
        model.addAttribute("assignments", assignments);
        model.addAttribute("additionalStudents", additionalStudents);
        model.addAttribute("studentGradesMap", studentGradesMap);
        return "fragments/additional-grades";
    }
}
