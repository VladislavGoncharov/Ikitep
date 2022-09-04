package com.maksatkyrgyzbaev.ikitep.dto;

import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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

    public void sortBooks(){
        this.setBooks(this.getBooks().stream()
                .sorted((o1, o2) -> { // Первая сортировка по ID (последне добавленные вверх)
                    if (o1.getId() > o2.getId()) return 1;
                    return -1;
                })
                .sorted((o1, o2) -> { // Далее сортировка по бронированию (Все бронированные книги вниз)
                    if (o1.getBookedBook().size() == 0) return -1; // если ни разу не бронировали вверх
                    BookedBook bookedBook = o1.getBookedBook().get(o1.getBookedBook().size() - 1);
                    if (bookedBook.isBookingIsActive()) return 1; // если бронирования были,
                        // проверяем последнюю бронь и ее активность
                    else return -1;
                })
                .collect(Collectors.toList()));
    }

    public static Long getCountBookedBooksIsActive(List<BookedBook> bookedBook) {
        if (bookedBook == null) return 0L;
        return bookedBook.stream().filter(BookedBook::isBookingIsActive).count();

    }
}
