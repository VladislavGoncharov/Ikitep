package com.maksatkyrgyzbaev.ikitep.controller.admin;

import com.maksatkyrgyzbaev.ikitep.service.BookService;
import com.maksatkyrgyzbaev.ikitep.service.BookedBookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/book")
public class BookAdminController {

    private final BookService bookService;
    private final BookedBookService bookedBookService;

    public BookAdminController(BookService bookService, BookedBookService bookedBookService) {
        this.bookService = bookService;
        this.bookedBookService = bookedBookService;
    }

    @GetMapping
    public String bookMain(Model model) {
        model.addAttribute("allBooks",bookService.findAll());
        model.addAttribute("countBookedBook",bookedBookService.getCountBookedBook());
        return "admin-book";
    }

    @RequestMapping("/delete-{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/admin/book";
    }

}
