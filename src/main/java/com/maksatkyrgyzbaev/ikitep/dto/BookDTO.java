package com.maksatkyrgyzbaev.ikitep.dto;

import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookDTO {
    private Long id;
    private String serialNumber;
    private String author;
    private String bookName;
    private String schoolName;
    private List<BookedBook> bookedBook;
    private int likes;

}
