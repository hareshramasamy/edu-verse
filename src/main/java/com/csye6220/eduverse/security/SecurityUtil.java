package com.csye6220.eduverse.security;

import com.csye6220.eduverse.entity.CourseOffering;
import com.csye6220.eduverse.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private final CourseOfferingService courseOfferingService;

    @Autowired
    public SecurityUtil(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public static Authentication getSessionUser() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication;
        }
        return null;
    }

    public boolean checkUserNotAuthorisedForCourse(Long courseOfferingId) {
        Authentication authentication = getSessionUser();
        if(authentication != null) {
            CourseOffering courseOffering = courseOfferingService.getCourseOfferingById(courseOfferingId);
            boolean isEnrolled = false;
            boolean isCourseInstructor = false;
            if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_INSTRUCTOR".equals(grantedAuthority.getAuthority()))) {
                isCourseInstructor = courseOfferingService.checkCurrentUserIsCourseInstructor(courseOffering.getInstructor().getId(), authentication.getName());
            } else if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_STUDENT".equals(grantedAuthority.getAuthority()))) {
                isEnrolled = courseOfferingService.checkCurrentUserEnrollment(courseOfferingId, authentication.getName());
            }
            return !isEnrolled && !isCourseInstructor;
        }
        return true;
    }

    public static boolean isAuthorisedStudent(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_STUDENT".equals(grantedAuthority.getAuthority()));
    }

    public static boolean isAuthorisedInstructor(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority -> "ROLE_INSTRUCTOR".equals(grantedAuthority.getAuthority()));
    }
}
