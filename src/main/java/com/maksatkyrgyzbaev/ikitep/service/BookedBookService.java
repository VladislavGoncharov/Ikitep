package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.BookedBookDTO;

import java.util.List;

public interface BookedBookService {
    List<BookedBookDTO> findAll();

    BookedBookDTO getById(Long id);

    Long getCountBookedBook();

    List<BookedBookDTO> getAllBySearchingInSchoolById(Long schoolId, String fieldSearch);

    List<BookedBookDTO> getAllBySchool(Long schoolId);

    void save(BookedBookDTO bookedBookDTO);

    void update(BookedBookDTO bookedBookDTO);

    void deleteById(Long id);

    void returnBookById(Long bookedId);
}
