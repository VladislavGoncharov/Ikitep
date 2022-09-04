package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.BookedBookDTO;
import com.maksatkyrgyzbaev.ikitep.entity.*;
import com.maksatkyrgyzbaev.ikitep.mapper.BookedBookMapper;
import com.maksatkyrgyzbaev.ikitep.repository.BookRepository;
import com.maksatkyrgyzbaev.ikitep.repository.BookedBookRepository;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import com.maksatkyrgyzbaev.ikitep.repository.UserRepository;
import com.maksatkyrgyzbaev.ikitep.util.Search;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class BookedBookServiceImpl implements BookedBookService {

    private final BookedBookMapper MAPPER = BookedBookMapper.MAPPER;

    private final BookedBookRepository bookedBookRepository;
    private final BookRepository bookRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    public BookedBookServiceImpl(BookedBookRepository bookedBookRepository, BookRepository bookRepository, SchoolRepository schoolRepository, UserRepository userRepository) {
        this.bookedBookRepository = bookedBookRepository;
        this.bookRepository = bookRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BookedBookDTO> findAll() {
        return MAPPER.fromBookedBookList(bookedBookRepository.findAll())
                .stream()
                .sorted(BookedBookDTO::compareTo)
                .sorted(BookedBookDTO::compareByIsBookedBook)
                .collect(Collectors.toList());
    }

    @Override
    public BookedBookDTO getById(Long id) {
        return MAPPER.fromBookedBook(bookedBookRepository.getById(id));
    }

    @Override
    public Long getCountBookedBook() {
        return bookedBookRepository.count();
    }

    @Override
    public List<BookedBookDTO> getAllBySearchingInSchoolById(Long schoolId, String fieldSearch) {
        String fieldSearchToLC = fieldSearch.toLowerCase();

        return MAPPER.fromBookedBookList(bookedBookRepository.getAllBySchool(schoolRepository.getById(schoolId)))
                .stream()
                .filter(bookedBookDTO -> Search.containsBookedBookFieldsWithFieldSearch(bookedBookDTO, fieldSearchToLC))
                .sorted(BookedBookDTO::compareTo)
                .sorted(BookedBookDTO::compareByIsBookedBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookedBookDTO> getAllBySchool(Long schoolId) {
        return MAPPER.fromBookedBookList(bookedBookRepository.getAllBySchool(schoolRepository.getById(schoolId)))
                .stream()
                .sorted(BookedBookDTO::compareTo)
                .sorted(BookedBookDTO::compareByIsBookedBook)
                .collect(Collectors.toList());
    }

    @Override
    public void save(BookedBookDTO bookedBookDTO) {
        School school = schoolRepository.getById(bookedBookDTO.getSchoolId());
        User user = userRepository.findByFullName(bookedBookDTO.getUserFullName());
        Book book = bookRepository.getById(bookedBookDTO.getBook().getId());

        // Если ученик не добавлен в систему, мы его создаем
        if (user == null) {
            user = userRepository.save(User.builder()
                    .username(bookedBookDTO.getUserFullName())
                    .fullName(bookedBookDTO.getUserFullName())
                    .role(Role.STUDENT)
                    .school(school)
                    .password(new BCryptPasswordEncoder().encode("123")) // дефолтный пароль
                    .build());

            school.getUsers().add(user);
        }
        BookedBook bookedBook = bookedBookRepository.save(BookedBook.builder()
                .book(book)
                .school(school)
                .user(user)
                .returnDate(LocalDate.parse(bookedBookDTO.getReturnDateString()))
                .build());

        book.getBookedBook().add(bookedBook);
        book.setLikes(book.getLikes() + 1);
        user.getBookedBooks().add(bookedBook);
        school.getBookedBooks().add(bookedBook);

        bookRepository.save(book);
        userRepository.save(user);
        schoolRepository.save(school);
    }

    @Override
    public void update(BookedBookDTO bookedBookDTO) {
        BookedBook bookedBook = bookedBookRepository.getById(bookedBookDTO.getId());
        bookedBook.setReturnDate(LocalDate.parse(bookedBookDTO.getReturnDateString()));
        bookedBookRepository.save(bookedBook);
    }

    @Override
    public void deleteById(Long id) {
        BookedBook bookedBook = bookedBookRepository.getById(id);
        userRepository.deleteBookedBookById(bookedBook.getUser().getId(), bookedBook.getId());
        bookRepository.deleteBookedBookById(bookedBook.getBook().getId(), bookedBook.getId());
        schoolRepository.deleteBookedBookById(bookedBook.getSchool().getId(), bookedBook.getId());
        bookedBookRepository.deleteById(id);
    }

    @Override
    public void returnBookById(Long bookedId) {
        BookedBook bookedBook = bookedBookRepository.getById(bookedId);
        bookedBook.setBookingIsActive(false);
        bookedBook.setReturnDate(LocalDate.now());
        bookedBookRepository.save(bookedBook);
    }
}
