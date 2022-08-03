package com.maksatkyrgyzbaev.ikitep.repository;

import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedBookRepository extends JpaRepository<BookedBook, Long> {

    List<BookedBook> getAllBySchool(School school);
}
