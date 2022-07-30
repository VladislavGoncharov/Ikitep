package com.maksatkyrgyzbaev.ikitep.mapper;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SchoolMapper {

    SchoolMapper MAPPER = Mappers.getMapper(SchoolMapper.class);

//    default List<SchoolDTO> fromSchoolList(List<SchoolDTO> schoolDTOS) {
//        return reservation.stream()
//                .map(res -> ReservationDTO.builder()
//                        .id(res.getId())
//                        .reservationDate(res.getReservationDate())
//                        .startTime(res.getStartTime())
//                        .endTime(res.getEndTime())
//                        .amenityType(res.getAmenityType())
//                        .user(res.getUser())
//                        .build())
//                .collect(Collectors.toList());
//    }

    default School toSchool(SchoolDTO schoolDTO,School school) {
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
