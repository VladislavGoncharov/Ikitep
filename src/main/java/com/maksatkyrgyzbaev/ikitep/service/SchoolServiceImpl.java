package com.maksatkyrgyzbaev.ikitep.service;


import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.mapper.SchoolMapper;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
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
        List<School> list = schoolRepository.findAll();

        return list.stream().map(school -> SchoolDTO.builder()
                        .id(school.getId())
                        .schoolName(school.getSchoolName())
                        .schoolImg(school.getSchoolImg())
                        .countUsers((long) school.getUsers().size())
                        .countBooks((long) school.getBooks().size())
                        .countBookedBooks((long) school.getBookedBooks().size())
                        .build())
                .collect(Collectors.toList());
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
                .id(Long.parseLong(String.valueOf(object[0])))
                .schoolName(String.valueOf(object[1]))
                .build())
                .collect(Collectors.toList());
    }

    @Override
    public SchoolDTO getById(Long id) {
        return MAPPER.fromSchool(schoolRepository.getById(id));
    }

    @Override
    public SchoolDTO getBySchoolName(String schoolName) {
        return MAPPER.fromSchool(schoolRepository.getBySchoolName(schoolName));
    }


}
