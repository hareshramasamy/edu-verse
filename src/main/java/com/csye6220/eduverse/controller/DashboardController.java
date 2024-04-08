package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
public class DashboardController {

    DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public ModelAndView getDashboard() {
        Authentication authentication = SecurityUtil.getSessionUser();
        ModelAndView modelAndView = new ModelAndView("dashboard");

        if(Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_STUDENT".equals(grantedAuthority.getAuthority()))) {
            List<CourseOfferingDTO> coursesEnrolled = dashboardService.getEnrollmentsByStudent(authentication.getName());
            modelAndView.addObject("courses", coursesEnrolled);
        } else if (Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_INSTRUCTOR".equals(grantedAuthority.getAuthority()))) {
            List<CourseOfferingDTO> coursesCreated = dashboardService.getCoursesByInstructor(authentication.getName());
            modelAndView.addObject("courses", coursesCreated);
        }
        modelAndView.addObject("activeTab", "dashboard");
        return modelAndView;
    }
}
