package com.maksatkyrgyzbaev.ikitep.service;


import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.mapper.BookMapper;
import com.maksatkyrgyzbaev.ikitep.repository.BookRepository;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    BookMapper MAPPER = BookMapper.MAPPER;

    private final BookRepository bookRepository;
    private final SchoolRepository schoolRepository;

    public BookServiceImpl(BookRepository bookRepository, SchoolRepository schoolRepository) {
        this.bookRepository = bookRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Long getCountBooks() {
        return bookRepository.count();
    }

    @Override
    public List<String> getAllAuthors() {
        return bookRepository.getAllAuthors();
    }

    @Override
    public List<String> getAllBookName() {
        return bookRepository.getAllBookName();
    }

    @Override
    public List<BookDTO> findAllBySchool(Long id) {
        return MAPPER.fromBookList(bookRepository.findAll());
    }

    @Override
    public void save(BookDTO bookDTO) {
        bookRepository.save(Book.builder()
                .id(bookDTO.getId())
                .serialNumber(bookDTO.getSerialNumber())
                .author(bookDTO.getAuthor())
                .bookName(bookDTO.getBookName())
                .school(schoolRepository.getBySchoolName(bookDTO.getSchoolName()))
                .build());
    }

    @Override
    public BookDTO getById(Long id) {
        return MAPPER.fromBook(bookRepository.getById(id));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
