package com.maksatkyrgyzbaev.ikitep.controller.admin;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.dto.UserDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Role;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import com.maksatkyrgyzbaev.ikitep.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    private final UserService userService;
    private final SchoolService schoolService;

    public UserAdminController(UserService userService, SchoolService schoolService) {
        this.userService = userService;

        this.schoolService = schoolService;
    }

    @GetMapping
    public String userMain(Model model) {
        return addModel(model,new UserDTO(),null);
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute UserDTO userDTO, Model model) {
        try {
            if (userDTO.getId() != null) userService.update(userDTO);
            else userService.save(userDTO);
        } catch (ValidationException e) {
            return addModel(model,userDTO,e.getMessage());
        }

        return "redirect:/admin/user";
    }

    @RequestMapping("/update-{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        return addModel(model,userService.getById(id),null);
    }

    @RequestMapping("/delete-{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/user";
    }

    private String addModel(Model model,UserDTO userDTO,String error){
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allSchoolName", schoolService.findAllIdAndSchoolName());
        model.addAttribute("allRole", Role.getAllRole());
        model.addAttribute("newUser", userDTO);
        model.addAttribute("error", error);
        return "adminUser";
    }
}
