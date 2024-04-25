package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.entity.Student;
import com.csye6220.eduverse.pojo.*;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.CourseOfferingService;
import com.csye6220.eduverse.service.DashboardService;
import com.csye6220.eduverse.service.UserService;
import com.csye6220.eduverse.validator.ExcelFileValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@Controller
public class CourseOfferingController {
    CourseOfferingService courseOfferingService;
    DashboardService dashboardService;
    UserService userService;
    SecurityUtil securityUtil;
    @Autowired
    public CourseOfferingController(CourseOfferingService courseOfferingService, DashboardService dashboardService, UserService userService, SecurityUtil securityUtil) {
        this.courseOfferingService = courseOfferingService;
        this.dashboardService = dashboardService;
        this.userService = userService;
        this.securityUtil = securityUtil;
    }

    @GetMapping("/courses")
    public String getCourses(Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            if(SecurityUtil.isAuthorisedStudent(authentication)) {
                List<CourseOfferingDTO> coursesEnrolled = dashboardService.getEnrollmentsByStudent(authentication.getName());
                model.addAttribute("courses", coursesEnrolled);
            } else if(SecurityUtil.isAuthorisedInstructor(authentication)) {
                List<CourseOfferingDTO> coursesCreated = dashboardService.getCoursesByInstructor(authentication.getName());
                model.addAttribute("courses", coursesCreated);
            }
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
        }
        model.addAttribute("activeTab", "courses");
        return "courses";
    }

    @GetMapping("/courses/create")
    public String createNewCourseOffering(Model model, CourseOfferingDTO courseOfferingDTO) throws Exception {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            List<CourseDTO> courseDTOList = courseOfferingService.getAllCourses(authentication.getName());
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("coursesList", courseDTOList);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        model.addAttribute("courseOffering", courseOfferingDTO);
        return "create-new-course";
    }

    @PostMapping("/courses/create")
    public String createCourseOFfering(@Valid @ModelAttribute("course") CourseOfferingDTO courseOfferingDTO) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            courseOfferingService.createCourseOffering(courseOfferingDTO, authentication.getName());
        }
        return "redirect:/";
    }

    @GetMapping("/courses/{courseOfferingId}")
    public String getCourseOffering(@PathVariable Long courseOfferingId, Model model) {
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
        }
        return "course-home";
    }

    @GetMapping("/courses/{courseOfferingId}/people")
    public String getCoursePeoplePage(@PathVariable Long courseOfferingId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            List<Student> enrolledStudents = courseOfferingService.getEnrolledStudents(courseOfferingId, 0);
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("students", enrolledStudents);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "course-people";
    }

    @GetMapping("/loadMorePeople")
    public String loadMoreStudents(@RequestParam int offset, @RequestParam Long courseOfferingId, Model model) {
        List<Student> additionalStudents = courseOfferingService.getEnrolledStudents(courseOfferingId, offset);
        model.addAttribute("additionalStudents", additionalStudents);
        return "fragments/additional-people";
    }


    @GetMapping("/courses/{courseOfferingId}/people/add")
    public String addPeopleToCourse(@PathVariable Long courseOfferingId, PersonDTO personDTO, ExcelUploadValues excelUploadValues, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if (!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if (securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("person", personDTO);
            model.addAttribute("file", excelUploadValues);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "add-people";
    }

    @PostMapping("/courses/{courseOfferingId}/people/add")
    public String addSinglePersonToCourse(@PathVariable Long courseOfferingId, @Valid @ModelAttribute("person") PersonDTO personDTO, BindingResult result, Model model, ExcelUploadValues excelUploadValues) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if (result.hasErrors()) {
            model.addAttribute("person", personDTO);
            model.addAttribute("file", excelUploadValues);
            if (Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "add-people";
        }
        if (Objects.nonNull(authentication)) {
            courseOfferingService.enrollStudentsToCourse(courseOfferingId, List.of(personDTO.getEmail()));
        }
        return "redirect:/courses/" + courseOfferingId + "/people";
    }

    @PostMapping("/courses/{courseOfferingId}/people/addAll")
    @ResponseBody
    public String addAllPeopleToCourse(@PathVariable Long courseOfferingId) {
        List<String> emailIdsByDepartment = userService.getEmailIdsByDepartment(courseOfferingId);
        try {
            courseOfferingService.enrollStudentsToCourse(courseOfferingId, emailIdsByDepartment);
        } catch(Exception e) {
            return "Failed";
        }
        return "Success";
    }

    @PostMapping("/courses/{courseOfferingId}/people/upload-excel")
    public String addPeopleByUpload(@PathVariable Long courseOfferingId,  @Valid @ModelAttribute("file") ExcelUploadValues excelUploadValues, BindingResult result, Model model, PersonDTO personDTO) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            courseOfferingService.uploadExcelFile(excelUploadValues, courseOfferingId, result);
        }
        if (result.hasErrors()) {
            model.addAttribute("person",personDTO);
            model.addAttribute("file", excelUploadValues);
            if (Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "add-people";
        }
        return "redirect:/courses/" + courseOfferingId + "/people";
    }
}
