package com.maksatkyrgyzbaev.ikitep.dto;

import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import com.maksatkyrgyzbaev.ikitep.util.Search;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO extends Search implements Comparable<BookDTO> {
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

    // Стиль строчек с бронированием
    public String isBooking() {
        if (isBookBooked()) return "table-light text-secondary";
        return "";
    }

    @Override
    public int compareTo(BookDTO o) {
        if (this.getId() > o.getId()) return 1;
        return -1;
    }

    public int compareByIsBookedBook(BookDTO o) {
        if (this.isBookBooked()) return -1;
        return 1;
    }
}
