package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.security.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Objects;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String handleLogin() {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> Arrays.asList("ROLE_STUDENT", "ROLE_INSTRUCTOR").contains(grantedAuthority.getAuthority()))) {
            return "redirect:/";
        }
        return "custom-login";
    }
}
