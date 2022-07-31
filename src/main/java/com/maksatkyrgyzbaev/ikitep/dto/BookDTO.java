package com.maksatkyrgyzbaev.ikitep.dto;

import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String serialNumber;
    private String author;
    private String bookName;
    private String schoolName;
    private List<BookedBook> bookedBook;
    private int likes;
    private int numberOfSaves = 1;

    public Boolean isBookBooked() {
        if (bookedBook.size() == 0) return false;
        BookedBook bookedBook = this.bookedBook.get(this.bookedBook.size() - 1);
        return bookedBook.isBookingIsActive();
    }

    public BookedBook getLastBookedBook() {
        return this.bookedBook.get(this.bookedBook.size() - 1);
    }

}
