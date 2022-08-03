package com.maksatkyrgyzbaev.ikitep.repository;

import com.maksatkyrgyzbaev.ikitep.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(nativeQuery = true, value = "select author from books group by author")
    List<String> getAllAuthors();

    @Query(nativeQuery = true, value = "select book_name from books group by book_name")
    List<String> getAllBookName();

    @Modifying
    @Query(nativeQuery = true, value = "delete FROM BOOKS_BOOKED_BOOK where book_id=?1 and BOOKED_BOOK_id=?2")
    void deleteBookedBookById(Long bookId, Long bookedBookId);
}
