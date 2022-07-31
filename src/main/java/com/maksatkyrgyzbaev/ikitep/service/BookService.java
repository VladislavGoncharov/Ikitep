package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;

import java.util.List;

public interface BookService {
    Long getCountBooks();

    List<String> getAllAuthors();
    
    List<String> getAllBookName();

    List<BookDTO> findAllBySchool(Long id);

    void save(Long schoolId,BookDTO bookDTO );

    void update(BookDTO bookDTO, Long schoolId);

    BookDTO getById(Long id);

    BookDTO findById(Long id);

    void deleteById(Long id);

    void deleteById(Long id, Long schoolId);

    List<BookDTO> findAll();
}
