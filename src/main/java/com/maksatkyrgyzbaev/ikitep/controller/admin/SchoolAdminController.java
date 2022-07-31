package com.maksatkyrgyzbaev.ikitep.controller.admin;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@Controller
@RequestMapping("/admin/school")
public class SchoolAdminController {

    private final SchoolService schoolService;

    public SchoolAdminController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public String schoolMain(Model model) {
        model.addAttribute("allSchools", schoolService.findAllIdSchoolNameImgAndAllCount());
        model.addAttribute("newSchool", new SchoolDTO());
        return "admin-school";
    }

    @PostMapping("/save")
    public String saveSchool(@ModelAttribute SchoolDTO schoolDTO, Model model) {
        try {
            if (schoolDTO.getId() != null) schoolService.update(schoolDTO);
            else schoolService.save(schoolDTO);
        } catch (ValidationException e) {
            model.addAttribute("allSchools", schoolService.findAllIdSchoolNameImgAndAllCount());
            model.addAttribute("newSchool", schoolDTO);
            model.addAttribute("error", e.getMessage());
            return "admin-school";
        }

        return "redirect:/admin/school";
    }

    @RequestMapping("/update-{id}")
    public String updateSchool(@PathVariable("id") Long id, Model model) {
        model.addAttribute("allSchools", schoolService.findAllIdSchoolNameImgAndAllCount());
        model.addAttribute("newSchool", schoolService.getSchoolWithNameAndImgById(id));
        return "admin-school";
    }

    @RequestMapping("/delete-{id}")
    public String deleteSchool(@PathVariable("id") Long id) {
        schoolService.deleteById(id);
        return "redirect:/admin/school";
    }
}
