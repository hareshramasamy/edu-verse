package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.CourseDTO;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.pojo.DepartmentDTO;
import com.csye6220.eduverse.pojo.RegistrationDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.CourseOfferingService;
import com.csye6220.eduverse.service.DashboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class CourseOfferingController {
    CourseOfferingService courseOfferingService;
    DashboardService dashboardService;

    @Autowired
    public CourseOfferingController(CourseOfferingService courseOfferingService, DashboardService dashboardService) {
        this.courseOfferingService = courseOfferingService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/courses")
    public String getCourses(Model model) throws Exception {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_STUDENT".equals(grantedAuthority.getAuthority()))) {
            List<CourseOfferingDTO> coursesEnrolled = dashboardService.getEnrollmentsByStudent(authentication.getName());
            model.addAttribute("courses", coursesEnrolled);
        } else if (Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_INSTRUCTOR".equals(grantedAuthority.getAuthority()))) {
            List<CourseOfferingDTO> coursesCreated = dashboardService.getCoursesByInstructor(authentication.getName());
            model.addAttribute("courses", coursesCreated);
        }
        model.addAttribute("activeTab", "courses");
        return "courses";
    }

    @GetMapping("/courses/create")
    public String createNewCourseOffering(Model model) throws Exception {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            List<CourseDTO> courseDTOList = courseOfferingService.getAllCourses(authentication.getName());
            model.addAttribute("coursesList", courseDTOList);
        }
        model.addAttribute("courseOffering", new CourseOfferingDTO());
        return "create-new-course";
    }

    @PostMapping("/courses/create")
    public String createCourseOFfering(@Valid @ModelAttribute("course") CourseOfferingDTO courseOfferingDTO, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        courseOfferingService.createCourseOffering(courseOfferingDTO, authentication.getName());
        return "redirect:/";
    }
}
