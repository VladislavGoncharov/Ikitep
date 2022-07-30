package com.maksatkyrgyzbaev.ikitep.service;


import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.mapper.SchoolMapper;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private final SchoolMapper MAPPER = SchoolMapper.MAPPER;

    private final SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<SchoolDTO> findAllIdSchoolNameImgAndAllCount() {
        List<Object[]> list = schoolRepository.findAllIdSchoolNameImgAndAllCount();

        return list.stream().map(objects -> SchoolDTO.builder()
                        .id(returnLong(objects[0]))
                        .schoolName(String.valueOf(objects[1]))
                        .schoolImg(String.valueOf(objects[2]))
                        .countUsers(returnLong(objects[3]))
                        .countBooks(returnLong(objects[4]))
                        .countBookedBooks(returnLong(objects[5]))
                        .build())
                .collect(Collectors.toList());
    }

    private Long returnLong(Object o) {
        return Long.parseLong(String.valueOf(o));
    }

    @Override
    public Long getCountSchools() {
        return schoolRepository.count();
    }

    @Override
    public void save(SchoolDTO schoolDTO) throws ValidationException {
        if (schoolRepository.existsBySchoolName(schoolDTO.getSchoolName()))
            throw new ValidationException("Такая школа уже существует");
        if (schoolDTO.getSchoolImg() == null || schoolDTO.getSchoolImg().isEmpty())
            schoolDTO.setSchoolImg("school_1.png");

        schoolRepository.save(School.builder()
                .schoolName(schoolDTO.getSchoolName())
                .schoolImg(schoolDTO.getSchoolImg())
                .build());
    }

    @Override
    public void update(SchoolDTO schoolDTO) throws ValidationException {
        if (schoolRepository.existsBySchoolNameAndIdNot(schoolDTO.getSchoolName(), schoolDTO.getId()))
            throw new ValidationException("Такая школа уже существует");

        if (schoolDTO.getSchoolImg() == null || schoolDTO.getSchoolImg().isEmpty())
            schoolDTO.setSchoolImg("school_1.png");

        schoolRepository.save(MAPPER.toSchool(schoolDTO, schoolRepository.getById(schoolDTO.getId())));
    }

    @Override
    public void deleteById(Long id) {
        schoolRepository.deleteById(id);
    }

    @Override
    public SchoolDTO getSchoolWithNameAndImgById(Long id) {
        String school = schoolRepository.getSchoolWithNameAndImgById(id);
        String[] strings = school.split(",");

        return SchoolDTO.builder()
                .id(id)
                .schoolName(String.valueOf(strings[0]))
                .schoolImg(String.valueOf(strings[1]))
                .build();
    }

    @Override
    public List<SchoolDTO> findAllIdAndSchoolName() {
        List<Object[]> objects = schoolRepository.findAllIdAndSchoolName();
        return objects.stream().map(object -> SchoolDTO.builder()
                .id(returnLong(object[0]))
                .schoolName(String.valueOf(object[1]))
                .build())
                .collect(Collectors.toList());
    }


}
