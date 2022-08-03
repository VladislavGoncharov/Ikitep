package com.maksatkyrgyzbaev.ikitep.util;


import com.maksatkyrgyzbaev.ikitep.dto.BookedBookDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Search {

    private String fieldSearch;

    // Поиск книги по серийному номеру, названию книги и автору
    public static boolean containsBookFieldsWithFieldSearch(Book book, String fieldSearch) {
        return book.getSerialNumber().toLowerCase().contains(fieldSearch) ||
                book.getBookName().toLowerCase().contains(fieldSearch) ||
                book.getAuthor().toLowerCase().contains(fieldSearch);
    }

    // Поиск бронирования по серийному номеру, названию книги, автору и имени ученика
    public static boolean containsBookedBookFieldsWithFieldSearch(BookedBookDTO bookedBookDTO, String fieldSearch) {
        return bookedBookDTO.getBook().getSerialNumber().toLowerCase().contains(fieldSearch) ||
                bookedBookDTO.getBook().getBookName().toLowerCase().contains(fieldSearch) ||
                bookedBookDTO.getBook().getAuthor().toLowerCase().contains(fieldSearch) ||
                bookedBookDTO.getUserFullName().toLowerCase().contains(fieldSearch);
    }

}

