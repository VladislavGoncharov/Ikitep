package com.maksatkyrgyzbaev.ikitep.controller.admin;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.ValidationException;

@Controller
@RequestMapping("/admin/school")
public class SchoolAdminController {

    private final SchoolService schoolService;

    public SchoolAdminController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    private String schoolMain(Model model) {
        model.addAttribute("allSchool",schoolService.findAllIdSchoolNameImgAndAllCount());
        model.addAttribute("newSchool",new SchoolDTO());
        return "adminSchool";
    }

    @PostMapping("/save")
    private String saveSchool(@ModelAttribute SchoolDTO schoolDTO,Model model){

        try{
            schoolService.save(schoolDTO);
        }catch (ValidationException e){
            model.addAttribute("allSchool",schoolService.findAllIdSchoolNameImgAndAllCount());
            model.addAttribute("newSchool",schoolDTO);
            model.addAttribute("error",e.getMessage());
            return "adminSchool";
        }

        return "redirect:/admin/school";
    }
}
