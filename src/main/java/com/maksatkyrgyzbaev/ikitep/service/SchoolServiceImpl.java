package com.maksatkyrgyzbaev.ikitep.service;


import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

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

    private Long returnLong(Object o){
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

        schoolRepository.save(School.builder()
                .schoolName(schoolDTO.getSchoolName())
                .schoolImg(schoolDTO.getSchoolImg())
                .build());
    }

    @Override
    public void update(SchoolDTO schoolDTO) throws ValidationException {
//        if (schoolRepository)
    }


}
