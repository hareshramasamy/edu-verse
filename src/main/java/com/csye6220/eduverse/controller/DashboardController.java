package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String getDashboard(Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_STUDENT".equals(grantedAuthority.getAuthority()))) {
            List<CourseOfferingDTO> coursesEnrolled = dashboardService.getEnrollmentsByStudent(authentication.getName());
            model.addAttribute("courses", coursesEnrolled);
        } else if (Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_INSTRUCTOR".equals(grantedAuthority.getAuthority()))) {
            List<CourseOfferingDTO> coursesCreated = dashboardService.getCoursesByInstructor(authentication.getName());
            model.addAttribute("courses", coursesCreated);
        }
        model.addAttribute("activeTab", "dashboard");
        return "dashboard";
    }
}
