package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();

    BookDTO getById(Long id);

    Long getCountBooks();

    List<String> getAllAuthors();

    List<String> getAllBookName();

    void save(Long schoolId,BookDTO bookDTO );

    void update(BookDTO bookDTO, Long schoolId);

    void deleteById(Long id);

    void deleteById(Long id, Long schoolId);
}
