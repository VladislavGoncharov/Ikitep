package com.maksatkyrgyzbaev.ikitep.dto;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String author;
    private String bookName;
    private SchoolDTO school;
    private List<BookedBookDTO> bookedBook;
    private int totalNumber;
    private int remains;
}
