package com.maksatkyrgyzbaev.ikitep.controller;

import com.maksatkyrgyzbaev.ikitep.dto.BookedBookDTO;
import com.maksatkyrgyzbaev.ikitep.service.BookService;
import com.maksatkyrgyzbaev.ikitep.service.BookedBookService;
import com.maksatkyrgyzbaev.ikitep.service.SchoolService;
import com.maksatkyrgyzbaev.ikitep.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BookedBookController {

    private final BookedBookService bookedBookService;
    private final BookService bookService;
    private final UserService userService;
    private final SchoolService schoolService;

    public BookedBookController(BookedBookService bookedBookService, BookService bookService, UserService userService, SchoolService schoolService) {
        this.bookedBookService = bookedBookService;
        this.bookService = bookService;
        this.userService = userService;
        this.schoolService = schoolService;
    }

    @RequestMapping("/booked-book")
    public String getAllBookedSchoolLibrary(Model model, HttpServletRequest request){
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        model.addAttribute("schoolName",schoolService.getSchoolNameById(schoolId));
        model.addAttribute("allBookedBooks",bookedBookService.getAllBySchool(schoolId));
        return "booked-book";
    }

    @RequestMapping("/booked-book-{bookId}")
    public String getAllBookedSchoolLibrary(@PathVariable Long bookId, Model model, HttpServletRequest request){
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");
        addModel(model,schoolId,new BookedBookDTO(schoolId,bookService.findById(bookId)));
        return "booked-book";
    }


    @RequestMapping("/save-booked")
    public String saveSchoolLibrary(@ModelAttribute BookedBookDTO bookedBookDTO){
        if (bookedBookDTO.getId()!=null) bookedBookService.update(bookedBookDTO);
        else bookedBookService.save(bookedBookDTO);
        return "redirect:/booked-book";
    }

    @RequestMapping("/update-booked-{bookId}")
    public String updateBookedSchoolLibrary(@PathVariable Long bookId, Model model, HttpServletRequest request){
        Long schoolId = (Long) request.getSession().getAttribute("schoolId");

        addModel(model,schoolId,bookedBookService.getById(bookId));
        model.addAttribute("switchUpdate",true);

        return "booked-book";
    }

    @RequestMapping("/delete-booked-{bookId}")
    public String deleteBookedSchoolLibrary(@PathVariable Long bookId){
        bookedBookService.deleteById(bookId);
        return "redirect:/booked-book";
    }

    private void addModel(Model model,Long schoolId,BookedBookDTO bookedBookDTO){
        model.addAttribute("schoolName",schoolService.getSchoolNameById(schoolId));
        model.addAttribute("allBookedBooks",bookedBookService.getAllBySchool(schoolId));
        model.addAttribute("allUserBySchool",userService.getAllFullNameBySchoolId(schoolId));
        model.addAttribute("newBookedBook",bookedBookDTO);
    }
}
