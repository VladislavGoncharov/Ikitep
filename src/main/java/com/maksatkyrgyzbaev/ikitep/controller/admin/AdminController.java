package com.maksatkyrgyzbaev.ikitep.controller.admin;

import com.maksatkyrgyzbaev.ikitep.service.BookService;
import com.maksatkyrgyzbaev.ikitep.service.BookedBookService;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import com.maksatkyrgyzbaev.ikitep.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SchoolService schoolService;
    private final UserService userService;
    private final BookService bookService;
    private final BookedBookService bookedBookService;

    public AdminController(SchoolService schoolService, UserService userService,
                           BookService bookService, BookedBookService bookedBookService) {
        this.schoolService = schoolService;
        this.userService = userService;
        this.bookService = bookService;
        this.bookedBookService = bookedBookService;
    }

    @RequestMapping
    public String adminPage(Model model){
        model.addAttribute("school",schoolService.getCountSchools());
        model.addAttribute("user",userService.getCountUsers());
        model.addAttribute("book",bookService.getCountBooks());
        model.addAttribute("bookedBook",bookedBookService.getCountBookedBook());
        return "adminPage";
    }
}
