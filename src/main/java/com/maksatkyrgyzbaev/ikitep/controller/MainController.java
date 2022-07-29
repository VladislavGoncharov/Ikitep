package com.maksatkyrgyzbaev.ikitep.controller;

import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import com.maksatkyrgyzbaev.ikitep.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {

    private final UserService userService;
    private final SchoolService schoolService;

    public MainController(UserService userService, SchoolService schoolService) {
        this.userService = userService;
        this.schoolService = schoolService;
    }

    @RequestMapping("/")
    public String chooseSchool(Model model, Principal principal){
        model.addAttribute("user",userService.findUserByUsername(principal.getName()));
        model.addAttribute("schools",schoolService.findAllIdSchoolNameImgAndAllCount());
        return "choose-school";
    }
}
