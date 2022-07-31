package com.maksatkyrgyzbaev.ikitep.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookedBookDTO {

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
}
