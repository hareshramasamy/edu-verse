package com.csye6220.eduverse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String handleLogin() {
        return "custom-login";
    }
}
