package com.maksatkyrgyzbaev.ikitep.dto;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolDTO {
    private Long id;
    private String schoolName;
    private List<UserDTO> users;
    private List<BookDTO> books;
    private List<BookedBookDTO> bookedBooks;
}
