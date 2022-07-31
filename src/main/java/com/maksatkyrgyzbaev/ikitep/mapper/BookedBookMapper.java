package com.maksatkyrgyzbaev.ikitep.mapper;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;
import com.maksatkyrgyzbaev.ikitep.dto.BookedBookDTO;
import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface BookedBookMapper {

    BookedBookMapper MAPPER = Mappers.getMapper(BookedBookMapper.class);

    default List<BookedBookDTO> fromBookedBookList(List<BookedBook> bookedBooks) {
        return bookedBooks.stream()
                .map(this::fromBookedBook).collect(Collectors.toList());
    }

    default BookedBookDTO fromBookedBook(BookedBook bookedBook) {
        return BookedBookDTO.builder()
                .id(bookedBook.getId())
                .book(BookDTO.builder()
                        .id(bookedBook.getBook().getId())
                        .bookName(bookedBook.getBook().getBookName())
                        .author(bookedBook.getBook().getAuthor())
                        .serialNumber(bookedBook.getBook().getSerialNumber())
                        .build())
                .userFullName(bookedBook.getUser().getFullName())
                .schoolId(bookedBook.getSchool().getId())
                .schoolName(bookedBook.getSchool().getSchoolName())
                .dateOfCreation(bookedBook.getDateOfCreation())
                .returnDate(bookedBook.getReturnDate())
                .bookingIsActive(bookedBook.isBookingIsActive())
                .build();
    }

//    default Book toBook(BookDTO BookDTO) {
//        return Book.builder()
//                .id(schoolDTO.getId())
//                .schoolName(schoolDTO.getSchoolName())
//                .schoolImg(schoolDTO.getSchoolImg())
//                .books(school.getBooks())
//                .users(school.getUsers())
//                .bookedBooks(school.getBookedBooks())
//                .build();
//    }
}
