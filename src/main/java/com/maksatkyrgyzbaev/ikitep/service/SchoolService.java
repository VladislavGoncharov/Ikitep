package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.entity.School;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface SchoolService {

    List<SchoolDTO> findAllIdSchoolNameImgAndAllCount();

    SchoolDTO findIdSchoolNameImgAndAllCountBySchoolName(String schoolName);

    List<SchoolDTO> findAllIdAndSchoolName();

    SchoolDTO getById(Long id);

    String  getSchoolNameById(Long schoolId);

    SchoolDTO getSchoolBooksBySearchingInSchoolById(Long schoolId, String fieldSearch);

    SchoolDTO getSchoolWithNameAndImgById(Long id);

    Long getCountSchools();

    void save(SchoolDTO schoolDTO) throws ValidationException;

    void update(SchoolDTO schoolDTO) throws ValidationException;

    void deleteById(Long id);

    School getSchoolById(Long schoolId);

    void updateSchool(Long schoolId, String entityName, List<?> entities);
}
