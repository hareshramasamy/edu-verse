package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.AnnouncementDTO;
import com.csye6220.eduverse.pojo.UserDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.AnnouncementService;
import com.csye6220.eduverse.service.CourseOfferingService;
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
public class AnnouncementsController {

    CourseOfferingService courseOfferingService;
    AnnouncementService announcementService;
    SecurityUtil securityUtil;
    UserService userService;

    @Autowired
    public AnnouncementsController(CourseOfferingService courseOfferingService, SecurityUtil securityUtil, UserService userService, AnnouncementService announcementService) {
        this.courseOfferingService = courseOfferingService;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.announcementService = announcementService;
    }

    @GetMapping("/courses/{courseOfferingId}/announcements")
    public String getAnnouncementsForCourseOffering(@PathVariable Long courseOfferingId, Model model){
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByCourseOffering(courseOfferingId);
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("announcements", announcements);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "course-announcements";
    }

    @GetMapping("/courses/{courseOfferingId}/announcements/create")
    public String createAnnouncementPage(@PathVariable Long courseOfferingId, AnnouncementDTO announcementDTO, Model model) {
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
        model.addAttribute("announcement", announcementDTO);
        model.addAttribute("courseOfferingId", courseOfferingId);
        return "create-announcement";
    }

    @PostMapping("/courses/{courseOfferingId}/announcements/create")
    public String createAnnouncement(@PathVariable Long courseOfferingId, @Valid @ModelAttribute("announcement") AnnouncementDTO announcementDTO, BindingResult result, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(result.hasErrors()) {
            model.addAttribute("announcement", announcementDTO);
            model.addAttribute("courseOfferingId", courseOfferingId);
            if(Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "create-announcement";
        }
        if(Objects.nonNull(authentication)) {
            announcementService.createAnnouncement(announcementDTO, authentication.getName());
        }
        return "redirect:/courses/" + courseOfferingId + "/announcements";
    }

    @GetMapping("/courses/{courseOfferingId}/announcements/{announcementId}")
    public String getAnnouncementById(@PathVariable Long courseOfferingId, @PathVariable Long announcementId, Model model){
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            AnnouncementDTO announcement = announcementService.getAnnouncementById(announcementId);
            if(announcement == null) {
                return "error/404";
            }
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("announcement", announcement);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "single-course-announcement";
    }

    @GetMapping("/courses/{courseOfferingId}/announcements/{announcementId}/edit")
    public String editAnnouncementPage(@PathVariable Long courseOfferingId, @PathVariable Long announcementId, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            AnnouncementDTO announcement = announcementService.getAnnouncementForEditPage(announcementId);
            if(announcement == null) {
                return "error/404";
            }
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
            model.addAttribute("announcement", announcement);
            model.addAttribute("courseOfferingId", courseOfferingId);
        }
        return "edit-announcement";
    }

    @PostMapping("/courses/{courseOfferingId}/announcements/{announcementId}/edit")
    public String editAnnouncement(@PathVariable Long courseOfferingId, @PathVariable Long announcementId, @Valid @ModelAttribute("announcement") AnnouncementDTO announcementDTO, BindingResult result, Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(result.hasErrors()) {
            announcementDTO.setId(announcementId);
            model.addAttribute("announcement", announcementDTO);
            model.addAttribute("courseOfferingId", courseOfferingId);
            if(Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "edit-announcement";
        }
        if(Objects.nonNull(authentication)) {
            AnnouncementDTO updatedAnnouncementDTO = announcementService.editAnnouncementById(announcementDTO, announcementId, authentication.getName());
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("announcement", updatedAnnouncementDTO);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));

        }
        model.addAttribute("activeTab", "courses");
        return "redirect:/courses/" + courseOfferingId + "/announcements/" + announcementId;
    }

    @GetMapping ("/courses/{courseOfferingId}/announcements/{announcementId}/delete")
    public String deleteAnnouncement(@PathVariable Long courseOfferingId, @PathVariable Long announcementId, Model model) {
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
            announcementService.deleteAnnouncementById(announcementId);
            List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByCourseOffering(courseOfferingId);
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("announcements", announcements);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "redirect:/courses/" + courseOfferingId + "/announcements";
    }
}
