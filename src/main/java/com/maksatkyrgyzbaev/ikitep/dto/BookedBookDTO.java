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
    private SchoolDTO school;
    private BookDTO book;
    private UserDTO user;
    private BookedBookDTO bookedBook;
    private LocalDate dateOfCreation;
    private LocalDate returnDate;
    private boolean bookingIsActive;

}
