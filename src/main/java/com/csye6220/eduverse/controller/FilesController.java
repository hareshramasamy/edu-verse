package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.entity.FileData;
import com.csye6220.eduverse.pojo.FileDTO;
import com.csye6220.eduverse.pojo.UserDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.CourseOfferingService;
import com.csye6220.eduverse.service.FileService;
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
import java.util.List;
import java.util.Objects;

@Controller
public class FilesController {

    CourseOfferingService courseOfferingService;

    SecurityUtil securityUtil;

    FileService fileService;

    UserService userService;

    @Autowired
    public FilesController(CourseOfferingService courseOfferingService, SecurityUtil securityUtil, FileService fileService, UserService userService) {
        this.courseOfferingService = courseOfferingService;
        this.securityUtil = securityUtil;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/courses/{courseOfferingId}/files")
    public String getFilesForCourseOffering(@PathVariable Long courseOfferingId, Model model){
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
                return "error/404";
            }
            if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
                return "error/403";
            }
            List<FileDTO> files = fileService.getFilesByCourseOffering(courseOfferingId);
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            model.addAttribute("files", files);
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            model.addAttribute("activeTab", "courses");
        }
        return "course-files";
    }

    @GetMapping("/courses/{courseOfferingId}/files/upload")
    public String uploadFilePage(@PathVariable Long courseOfferingId, Model model, FileDTO fileDTO) {
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
        model.addAttribute("file", fileDTO);
        model.addAttribute("courseOfferingId", courseOfferingId);
        return "file-upload";
    }

    @PostMapping("/courses/{courseOfferingId}/files/upload")
    public String uploadFile(@PathVariable Long courseOfferingId, @Valid @ModelAttribute("file") FileDTO fileDTO, BindingResult result, Model model) throws IOException {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(result.hasErrors()) {
            model.addAttribute("file", fileDTO);
            model.addAttribute("courseOfferingId", courseOfferingId);
            if(Objects.nonNull(authentication)) {
                UserDTO userDTO = userService.searchByUserName(authentication.getName());
                model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
            }
            model.addAttribute("activeTab", "courses");
            model.addAttribute("course", courseOfferingService.getCourseOfferingDTOById(courseOfferingId));
            return "file-upload";
        }
        if(Objects.nonNull(authentication)) {
            fileService.uploadFile(fileDTO);
        }
        return "redirect:/courses/" + courseOfferingId + "/files";
    }


    @GetMapping("/courses/{courseOfferingId}/files/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId, @PathVariable Long courseOfferingId, HttpHeaders headers) {
        if(!courseOfferingService.checkCourseOfferingExists(courseOfferingId)) {
            return ResponseEntity.notFound().build();
        }
        if(securityUtil.checkUserNotAuthorisedForCourse(courseOfferingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        FileData file = fileService.getFileById(fileId);
        if(file == null) {
            return ResponseEntity.notFound().build();
        }
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(file.getData()));
    }

}
