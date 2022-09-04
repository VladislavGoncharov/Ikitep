package com.maksatkyrgyzbaev.ikitep.controller;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;
import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import com.maksatkyrgyzbaev.ikitep.mapper.BookMapper;
import com.maksatkyrgyzbaev.ikitep.service.BookService;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import com.maksatkyrgyzbaev.ikitep.util.ReadExcel;
import com.maksatkyrgyzbaev.ikitep.util.WriteExcel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@MultipartConfig
public class SchoolLibraryController {

    private final BookMapper MAPPER = BookMapper.MAPPER;

    private final SchoolService schoolService;
    private final BookService bookService;
    private final WriteExcel writeExcel;
    private final ReadExcel readExcel;

    public SchoolLibraryController(SchoolService schoolService, BookService bookService, WriteExcel writeExcel, ReadExcel readExcel) {
        this.schoolService = schoolService;
        this.bookService = bookService;
        this.writeExcel = writeExcel;
        this.readExcel = readExcel;
    }

    @RequestMapping("/school-{id}")
    public String schoolLibrary(@PathVariable Long id, Model model, HttpServletRequest request) {
        addModel(model, schoolService.getById(id), new BookDTO());
        request.getSession().setAttribute("schoolId", id);
        return "school-library";
    }

    @PostMapping("/save-book")
    public String schoolLibrarySaveBook(@ModelAttribute BookDTO bookDTO, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");

        if (bookDTO.getId() != null) bookService.update(bookDTO, schoolId);
        else bookService.save(schoolId, bookDTO);

        return "redirect:/school-" + schoolId;
    }

    @RequestMapping("/update-book-{id}")
    public String schoolLibraryUpdateBook(@PathVariable Long id, Model model, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        addModel(model, schoolService.getById(schoolId), bookService.getById(id));
        return "school-library";
    }

    @RequestMapping("/delete-book-{id}")
    public String schoolLibraryDeleteBook(@PathVariable Long id, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        bookService.deleteById(id, schoolId);
        return "redirect:/school-" + schoolId;
    }


    @RequestMapping("/search-book")
    public String searchBookInSchoolLibrary(@ModelAttribute BookDTO bookDTO, Model model, HttpServletRequest request) {
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        addModel(model,
                schoolService.getSchoolBooksBySearchingInSchoolById(schoolId, bookDTO.getFieldSearch())
                , new BookDTO());
        return "school-library";
    }

    @GetMapping("/download-books-{schoolId}")
    public void downloadBooks(@PathVariable Long schoolId,
                              HttpServletResponse response) {
        try {
            writeExcel.downloadFile(response, schoolService.getById(schoolId).getBooks());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download-users-{schoolId}")
    public void downloadUsers(@PathVariable Long schoolId,
                              HttpServletResponse response) {
        try {
            writeExcel.downloadFile(response, schoolService.getById(schoolId).getUsers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload-{entity}-{schoolId}")
    public String uploadBooks(@PathVariable("schoolId") Long schoolId,
                              @PathVariable("entity") String entityName,
                              @RequestParam("entityList") MultipartFile file,
                              HttpServletRequest request, Model model) {
        try {
            List<?> entities = readExcel.uploadFile(file, schoolService.getSchoolById(schoolId), entityName);
            schoolService.updateSchool(schoolId,entityName,entities);
        } catch (IOException e) {
            model.addAttribute("errorUpload", e.getMessage());
            addModel(model, schoolService.getById(schoolId), new BookDTO());
            request.getSession().setAttribute("schoolId", schoolId);
            return "school-library";
        }
        return "redirect:/school-" + schoolId;
    }

    private void addModel(Model model, SchoolDTO schoolDTO, BookDTO bookDTO) {
        model.addAttribute("school", schoolDTO);
        model.addAttribute("allBooks", MAPPER.fromBookList(schoolDTO.getBooks()));
        model.addAttribute("allAuthors", bookService.getAllAuthors());
        model.addAttribute("allBookName", bookService.getAllBookName());
        model.addAttribute("newBooks", bookDTO);
    }


}
