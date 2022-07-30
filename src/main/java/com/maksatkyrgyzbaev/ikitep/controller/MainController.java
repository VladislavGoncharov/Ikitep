package com.maksatkyrgyzbaev.ikitep.controller;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;
import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.service.BookService;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import com.maksatkyrgyzbaev.ikitep.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.ValidationException;
import java.security.Principal;

@Controller
public class MainController {

    private final UserService userService;
    private final SchoolService schoolService;
    private final BookService bookService;

    public MainController(UserService userService, SchoolService schoolService, BookService bookService) {
        this.userService = userService;
        this.schoolService = schoolService;
        this.bookService = bookService;
    }

    @RequestMapping("/")
    public String chooseSchool(Model model, Principal principal) {
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        model.addAttribute("schools", schoolService.findAllIdSchoolNameImgAndAllCount());
        return "choose-school";
    }

    @RequestMapping("school-{id}")
    public String schoolLibrary(@PathVariable Long id, Model model) {
        addModel(model, schoolService.getById(id), new BookDTO());
        return "school-library";
    }

    @PostMapping("save-book")
    public String schoolLibrarySaveBook(@ModelAttribute BookDTO bookDTO) {
        System.out.println(bookDTO.toString());
        bookService.save(bookDTO);

        SchoolDTO schoolDTO = schoolService.getBySchoolName(bookDTO.getSchoolName());
        return "redirect:/school-"+schoolDTO.getId();
    }

    @RequestMapping("update-book-{id}")
    public String schoolLibraryUpdateBook(@PathVariable Long id,Model model) {
        BookDTO bookDTO = bookService.getById(id);
        SchoolDTO schoolDTO = schoolService.getBySchoolName(bookDTO.getSchoolName());

        addModel(model,schoolDTO,bookDTO);
        return "redirect:/school-"+schoolDTO.getId();
    }

    @RequestMapping("delete-book-{id}")
    public String schoolLibraryDeleteBook(@PathVariable Long id) {
        SchoolDTO schoolDTO = schoolService.getBySchoolName(bookService.getById(id).getSchoolName());
        bookService.deleteById(id);

        return "redirect:/school-"+schoolDTO.getId();
    }

    private void addModel(Model model, SchoolDTO schoolDTO, BookDTO bookDTO) {
        model.addAttribute("school", schoolDTO);
        model.addAttribute("allBooks", schoolDTO.getBooks());
        model.addAttribute("allAuthors", bookService.getAllAuthors());
        model.addAttribute("allBookName", bookService.getAllBookName());
        model.addAttribute("newBooks", bookDTO);
    }


}
