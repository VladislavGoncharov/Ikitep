package com.maksatkyrgyzbaev.ikitep.mapper;

import com.maksatkyrgyzbaev.ikitep.dto.BookDTO;
import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.Book;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface BookMapper {

    BookMapper MAPPER = Mappers.getMapper(BookMapper.class);

    default List<BookDTO> fromBookList(List<Book> books) {
        return books.stream()
                .map(this::fromBook).collect(Collectors.toList());
    }

    default BookDTO fromBook(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .serialNumber(book.getSerialNumber())
                .author(book.getAuthor())
                .bookName(book.getBookName())
                .schoolName(book.getSchool().getSchoolName())
                .bookedBook(book.getBookedBook())
                .likes(book.getLikes())
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
