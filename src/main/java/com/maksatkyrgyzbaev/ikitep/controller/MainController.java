package com.maksatkyrgyzbaev.ikitep.controller;

import com.maksatkyrgyzbaev.ikitep.dto.UserDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Role;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import com.maksatkyrgyzbaev.ikitep.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;

@Controller
public class MainController {

    private final UserService userService;
    private final SchoolService schoolService;

    public MainController(UserService userService, SchoolService schoolService) {
        this.userService = userService;
        this.schoolService = schoolService;
    }

    @RequestMapping("/")
    public String chooseSchool(Model model, Principal principal) {
        UserDTO user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user", user);

        if (user.getRole().equals(Role.ADMIN)) // Для админа видны все школы
            model.addAttribute("schools", schoolService.findAllIdSchoolNameImgAndAllCount());
        else if (user.getRole().equals(Role.LIBRARIAN)) // Для библиотекаря видна только ее школа, чтобы случайно не перепутала ))
            model.addAttribute("schools",
                    Collections.singletonList(schoolService.findIdSchoolNameImgAndAllCountBySchoolName(user.getSchoolName())));
        return "choose-school";
    }
}
