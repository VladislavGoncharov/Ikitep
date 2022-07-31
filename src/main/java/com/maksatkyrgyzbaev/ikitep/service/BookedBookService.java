package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.BookedBookDTO;

import java.util.List;

public interface BookedBookService {
    Long getCountBookedBook();

    List<BookedBookDTO> getAllBySchool(Long schoolId);

    void save(BookedBookDTO bookedBookDTO);

    void update(BookedBookDTO bookedBookDTO);

    BookedBookDTO getById(Long id);

    void deleteById(Long id);

    List<BookedBookDTO> findAll();
}
