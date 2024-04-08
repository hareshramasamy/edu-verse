package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.CourseDTO;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class CourseOfferingController {

    CourseOfferingService courseOfferingService;

    @Autowired
    public CourseOfferingController(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
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
}
