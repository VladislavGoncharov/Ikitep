package com.maksatkyrgyzbaev.ikitep.mapper;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface SchoolMapper {

    SchoolMapper MAPPER = Mappers.getMapper(SchoolMapper.class);

    default List<SchoolDTO> fromSchoolList(List<School> school) {
        return school.stream()
                .map(this::fromSchool).collect(Collectors.toList());
    }

    default SchoolDTO fromSchool(School school) {
        return SchoolDTO.builder()
                .id(school.getId())
                .schoolName(school.getSchoolName())
                .schoolImg(school.getSchoolImg())
                .books(school.getBooks())
                .users(school.getUsers())
                .bookedBooks(school.getBookedBooks())
                .countUsers((long) school.getUsers().size())
                .countBooks((long) school.getBooks().size())
                .countBookedBooks(SchoolDTO.getCountBookedBooksIsActive(school.getBookedBooks()))
                .build();
    }

    default School toSchool(SchoolDTO schoolDTO, School school) {
        return School.builder()
                .id(schoolDTO.getId())
                .schoolName(schoolDTO.getSchoolName())
                .schoolImg(schoolDTO.getSchoolImg())
                .books(school.getBooks())
                .users(school.getUsers())
                .bookedBooks(school.getBookedBooks())
                .build();
    }
}
