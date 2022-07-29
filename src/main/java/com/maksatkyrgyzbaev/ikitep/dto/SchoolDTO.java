package com.maksatkyrgyzbaev.ikitep.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SchoolDTO {
    private Long id;
    private String schoolName;
    private List<UserDTO> users;
    private List<BookDTO> books;
    private List<BookedBookDTO> bookedBooks;
    private String schoolImg;

    private Long countUsers;
    private Long countBooks;
    private Long countBookedBooks;
}
