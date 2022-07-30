package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;

import java.util.List;

public interface BookService {
    Long getCountBooks();

    List<String> getAllAuthors();
    
    List<String> getAllBookName();

    List<BookDTO> findAllBySchool(Long id);

    void save(BookDTO bookDTO);

    BookDTO getById(Long id);

    void deleteById(Long id);
}
