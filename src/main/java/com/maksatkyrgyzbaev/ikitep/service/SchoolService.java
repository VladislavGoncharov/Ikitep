package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface SchoolService {

    List<SchoolDTO> findAllIdSchoolNameImgAndAllCount();

    Long getCountSchools();

    void save(SchoolDTO schoolDTO) throws ValidationException;
    void update(SchoolDTO schoolDTO) throws ValidationException;

    void deleteById(Long id);

    SchoolDTO getSchoolWithNameAndImgById(Long id);

    List<SchoolDTO> findAllIdAndSchoolName();

    SchoolDTO getById(Long id);

    SchoolDTO findById(Long id);

    SchoolDTO getBySchoolName(String schoolName);

    String  getSchoolNameById(Long schoolId);
}
