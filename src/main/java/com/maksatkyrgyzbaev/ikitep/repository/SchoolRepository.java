package com.maksatkyrgyzbaev.ikitep.repository;

import com.maksatkyrgyzbaev.ikitep.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query(value =
            "select s.id,s.school_name,s.school_img,count(users_id),count(books_id),count(booked_books_id) " +
                    "            from schools s" +
                    "            left join schools_users u on s.id=u.school_id " +
                    "            left join schools_books b on s.id=b.school_id " +
                    "            left join schools_booked_books bb on s.id=bb.school_id " +
                    "            group by s.school_name", nativeQuery = true)
    List<Object[]> findAllIdSchoolNameImgAndAllCount();

    Boolean existsBySchoolName(String schoolName);
    Boolean existsBySchoolNameAndId(String schoolName,Long id);
    Boolean existsBySchoolNameAndIdNot(String schoolName,Long id);

    @Query(value =
            "select s.school_name,s.school_img from schools s where s.id = :id" , nativeQuery = true)
    String getSchoolWithNameAndImgById(@Param("id") Long id);

    @Query(value = "select id, school_name from schools" , nativeQuery = true)
    List<Object[]> findAllIdAndSchoolName();
}
