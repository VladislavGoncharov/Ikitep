package com.maksatkyrgyzbaev.ikitep.controller.admin;

import com.maksatkyrgyzbaev.ikitep.service.BookService;
import com.maksatkyrgyzbaev.ikitep.service.BookedBookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/booked-book")
public class BookedBookAdminController {

    private final BookedBookService bookedBookService;

    public BookedBookAdminController(BookedBookService bookedBookService) {
        this.bookedBookService = bookedBookService;
    }

    @GetMapping
    public String bookMain(Model model) {
        model.addAttribute("allBookedBooks",bookedBookService.findAll());
        return "admin-booked-book";
    }

    @RequestMapping("/delete-{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        bookedBookService.deleteById(id);
        return "redirect:/admin/booked-book";
    }

}
