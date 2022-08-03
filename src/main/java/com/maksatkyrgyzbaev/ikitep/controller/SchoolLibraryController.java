package com.maksatkyrgyzbaev.ikitep.controller;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;
import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.mapper.BookMapper;
import com.maksatkyrgyzbaev.ikitep.service.BookService;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SchoolLibraryController {

    private final BookMapper MAPPER = BookMapper.MAPPER;

    private final SchoolService schoolService;
    private final BookService bookService;

    public SchoolLibraryController(SchoolService schoolService, BookService bookService) {
        this.schoolService = schoolService;
        this.bookService = bookService;
    }

    @RequestMapping("school-{id}")
    public String schoolLibrary(@PathVariable Long id, Model model, HttpServletRequest request) {
        addModel(model, schoolService.getById(id), new BookDTO());
        request.getSession().setAttribute("schoolId", id);
        return "school-library";
    }

    @PostMapping("save-book")
    public String schoolLibrarySaveBook(@ModelAttribute BookDTO bookDTO, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");

        if (bookDTO.getId() != null) bookService.update(bookDTO, schoolId);
        else bookService.save(schoolId, bookDTO);

        return "redirect:/school-" + schoolId;
    }

    @RequestMapping("update-book-{id}")
    public String schoolLibraryUpdateBook(@PathVariable Long id, Model model, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        addModel(model, schoolService.getById(schoolId), bookService.getById(id));
        return "school-library";
    }

    @RequestMapping("delete-book-{id}")
    public String schoolLibraryDeleteBook(@PathVariable Long id, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        bookService.deleteById(id, schoolId);
        return "redirect:/school-" + schoolId;
    }

    @RequestMapping("search-book")
    public String searchBookInSchoolLibrary(@ModelAttribute BookDTO bookDTO, Model model, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        addModel(model, schoolService.getSchoolBooksBySearchingInSchoolById(schoolId, bookDTO.getFieldSearch()), new BookDTO());
        return "school-library";
    }

    private void addModel(Model model, SchoolDTO schoolDTO, BookDTO bookDTO) {
        model.addAttribute("school", schoolDTO);
        model.addAttribute("allBooks", MAPPER.fromBookList(schoolDTO.getBooks()));
        model.addAttribute("allAuthors", bookService.getAllAuthors());
        model.addAttribute("allBookName", bookService.getAllBookName());
        model.addAttribute("newBooks", bookDTO);
    }


}
