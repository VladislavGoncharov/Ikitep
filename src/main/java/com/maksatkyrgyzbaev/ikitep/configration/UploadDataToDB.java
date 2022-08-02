package com.maksatkyrgyzbaev.ikitep.configration;

import com.maksatkyrgyzbaev.ikitep.entity.*;
import com.maksatkyrgyzbaev.ikitep.repository.BookRepository;
import com.maksatkyrgyzbaev.ikitep.repository.BookedBookRepository;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import com.maksatkyrgyzbaev.ikitep.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Configuration
public class UploadDataToDB {

    private final PasswordEncoder passwordEncoder;

    public UploadDataToDB(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadData(BookRepository bookRepository,
                                      BookedBookRepository bookedBookRepository,
                                      SchoolRepository schoolRepository,
                                      UserRepository userRepository) {
        return (args) -> {
            User user1 = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Сергей Александрович")
                    .role(Role.ADMIN)
                    .build();
            User user2 = User.builder()
                    .username("librarian1")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Людмила Степанова")
                    .role(Role.LIBRARIAN)
                    .build();
            User user3 = User.builder()
                    .username("librarian2")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Ирина Романовна")
                    .role(Role.LIBRARIAN)
                    .build();
            User user4 = User.builder()
                    .username("librarian3")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Лариса Петровна")
                    .role(Role.LIBRARIAN)
                    .build();

            School school1 = School.builder()
                    .schoolName("Школа № 1")
                    .schoolImg("school_1.png")
                    .users(List.of(user1,user2))
                    .build();
            School school2 = School.builder()
                    .schoolName("Школа № 2")
                    .schoolImg("school_1.png")
                    .users(List.of(user3))
                    .build();
            School school3 = School.builder()
                    .schoolName("Школа № 3")
                    .schoolImg("school_1.png")
                    .users(Collections.singletonList(user4))
                    .build();

            user1.setSchool(school1);
            user2.setSchool(school1);
            user3.setSchool(school2);
            user4.setSchool(school3);

            Book book1 = Book.builder()
                    .id(1L)
                    .bookName("Война и мир")
                    .author("Толстой Л.Н.")
                    .serialNumber("563424892")
                    .school(school1)
                    .build();
            Book book2 = Book.builder()
                    .id(2L)
                    .bookName("Учебник по физике 8 класс")
                    .author("Пёрышкин А.В.")
                    .serialNumber("324331343")
                    .school(school1)
                    .build();
            Book book3 = Book.builder()
                    .id(3L)
                    .bookName("Сказка о царе Салтане")
                    .author("Пушкин А.С.")
                    .serialNumber("995223423")
                    .school(school1)
                    .build();

            BookedBook bookedBook1 = BookedBook.builder()
                    .id(1L)
                    .book(book1)
                    .user(user1)
                    .returnDate(LocalDate.now().plusWeeks(2))
                    .school(book1.getSchool())
                    .build();
            BookedBook bookedBook2 = BookedBook.builder()
                    .id(2L)
                    .book(book2)
                    .user(user2)
                    .returnDate(LocalDate.now().minusDays(1))
                    .school(book2.getSchool())
                    .build();

            schoolRepository.save(school1);
            schoolRepository.save(school2);
            schoolRepository.save(school3);

            bookRepository.saveAll(List.of(book1,book2,book3));

            book1.setBookedBook(Collections.singletonList(bookedBook1));
            book2.setBookedBook(Collections.singletonList(bookedBook2));
            user1.setBookedBooks(Collections.singletonList(bookedBook1));
            user2.setBookedBooks(Collections.singletonList(bookedBook2));

            school1.setBooks(List.of(book1,book2,book3));
            school1.setBookedBooks(List.of(bookedBook1,bookedBook2));

            bookedBookRepository.save(bookedBook1);
            bookedBookRepository.save(bookedBook2);
            schoolRepository.save(school1);

        };
    }
}
