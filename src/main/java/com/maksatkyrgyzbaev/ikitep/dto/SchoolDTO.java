package com.maksatkyrgyzbaev.ikitep.dto;

import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import com.maksatkyrgyzbaev.ikitep.entity.User;
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
    private List<User> users;
    private List<Book> books;
    private List<BookedBook> bookedBooks;
    private String schoolImg;

    private Long countUsers;
    private Long countBooks;
    private Long countBookedBooks;
}
