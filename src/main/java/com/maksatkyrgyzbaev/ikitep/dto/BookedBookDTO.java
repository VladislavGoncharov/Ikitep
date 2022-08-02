package com.maksatkyrgyzbaev.ikitep.dto;

import com.maksatkyrgyzbaev.ikitep.util.Search;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookedBookDTO extends Search implements Comparable<BookedBookDTO> {

    private Long id;
    private Long schoolId;
    private String schoolName;
    private BookDTO book;
    private String userFullName;
    private LocalDate dateOfCreation;
    private LocalDate returnDate;
    private String returnDateString;
    private boolean bookingIsActive;

    public BookedBookDTO(Long schoolId, BookDTO book ) {
        this.schoolId = schoolId;
        this.book = book;
    }
    public BookedBookDTO(Long schoolId) {
        this.schoolId = schoolId;
    }

    // Стиль строчек со старыми бронированием и просроченным
    public String isBookingOldOrOverdueBooking() {
        if (LocalDate.now().isAfter(returnDate)) return "table-warning";
        if (isBookingIsActive()) return "";
        return "table-light text-secondary";
    }

    @Override
    public int compareTo(BookedBookDTO o) {
        if (this.getId() > o.getId()) return 1;
        return -1;
    }

    public int compareByIsBookedBook(BookedBookDTO o) {
        if (this.isBookingIsActive()) return -1;
        return 1;
    }
}
