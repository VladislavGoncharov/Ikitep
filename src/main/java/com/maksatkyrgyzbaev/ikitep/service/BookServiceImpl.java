package com.maksatkyrgyzbaev.ikitep.service;


import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.mapper.BookMapper;
import com.maksatkyrgyzbaev.ikitep.repository.BookRepository;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    BookMapper MAPPER = BookMapper.MAPPER;

    private final BookRepository bookRepository;
    private final SchoolRepository schoolRepository;
    private final BookedBookService bookedBookService;

    public BookServiceImpl(BookRepository bookRepository, SchoolRepository schoolRepository, BookedBookService bookedBookService) {
        this.bookRepository = bookRepository;
        this.schoolRepository = schoolRepository;
        this.bookedBookService = bookedBookService;
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
    public void save(Long schoolId, BookDTO bookDTO) {
        for (int i = 0; i < bookDTO.getNumberOfSaves(); i++) {

            Book book = bookRepository.save(Book.builder()
                    .serialNumber(bookDTO.getSerialNumber())
                    .author(bookDTO.getAuthor())
                    .bookName(bookDTO.getBookName())
                    .school(schoolRepository.getById(schoolId))
                    .build());

            schoolRepository.saveBookInSchool(schoolId, book.getId());
        }
    }

    @Override
    public void update(BookDTO bookDTO, Long schoolId) {
        Book oldBook = bookRepository.getById(bookDTO.getId());
        Book updateBook = Book.builder()
                .id(bookDTO.getId())
                .serialNumber(bookDTO.getSerialNumber())
                .author(bookDTO.getAuthor())
                .bookName(bookDTO.getBookName())
                .school(schoolRepository.getById(schoolId))
                .build();

        if (oldBook.getBookedBook()!=null)
            updateBook.setBookedBook(oldBook.getBookedBook());

        updateBook.setLikes(oldBook.getLikes());
        bookRepository.save(updateBook);
    }

    @Override
    public BookDTO getById(Long id) {
        return MAPPER.fromBook(bookRepository.getById(id));
    }

    @Override
    public BookDTO findById(Long id) {
        return MAPPER.fromBook(bookRepository.findById(id).get());
    }

    @Override
    public void deleteById(Long id) {
        Long schoolId = bookRepository.getById(id).getSchool().getId();
        deleteById(id, schoolId);
    }

    @Override
    public void deleteById(Long bookId, Long schoolId) {
        Book book = bookRepository.getById(bookId);
        schoolRepository.deleteBookByIdFromSchool(schoolId, bookId);
        if (book.getBookedBook().size() > 0) {
            for (BookedBook bookedBook : book.getBookedBook())
                bookedBookService.deleteById(bookedBook.getId());
        }
        bookRepository.delete(book);
    }

    @Override
    public List<BookDTO> findAll() {
       return MAPPER.fromBookList(bookRepository.findAll())
               .stream()
               .sorted(BookDTO::compareTo)
               .sorted(BookDTO::compareByIsBookedBook)
               .collect(Collectors.toList());
    }
}
