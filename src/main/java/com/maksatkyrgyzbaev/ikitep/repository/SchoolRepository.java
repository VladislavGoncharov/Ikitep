package com.maksatkyrgyzbaev.ikitep.repository;

import com.maksatkyrgyzbaev.ikitep.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    School getBySchoolName(String schoolName);

    @Query(value =
            "select s.id,s.school_name,s.school_img,count(users_id),count(books_id),count(booked_books_id) " +
                    "            from schools s" +
                    "            left join schools_users u on s.id=u.school_id " +
                    "            left join schools_books b on s.id=b.school_id " +
                    "            left join schools_booked_books bb on s.id=bb.school_id " +
                    "            group by s.school_name", nativeQuery = true)
    List<Object[]> findAllIdSchoolNameImgAndAllCount();


    boolean existsBySchoolName(String schoolName);
    boolean existsBySchoolNameAndIdNot(String schoolName,Long id);

    @Query(value =
            "select s.school_name,s.school_img from schools s where s.id = :id" , nativeQuery = true)
    String getSchoolWithNameAndImgById(@Param("id") Long id);

    @Query(value = "select id, school_name from schools" , nativeQuery = true)
    List<Object[]> findAllIdAndSchoolName();

    @Modifying
    @Query(nativeQuery = true,value = "delete FROM SCHOOLS_BOOKS where school_id=?1 and books_id=?2")
    void deleteBookByIdFromSchool(Long schoolId, Long bookId);

    @Modifying
    @Query(nativeQuery = true,value = "insert into SCHOOLS_BOOKS values(?1,?2)")
    void saveBookInSchool(Long schoolId, Long bookId);

    @Query(nativeQuery = true,value = "select school_name from schools where id = ?1")
    String getSchoolNameById(Long id);

    @Modifying
    @Query(nativeQuery = true,value = "delete FROM SCHOOLS_BOOKED_BOOKS  where school_id=?1 and BOOKED_BOOKS_id=?2")
    void deleteBookedBookById(Long schoolId, Long bookedBookId);

    @Modifying
    @Query(nativeQuery = true,value = "delete FROM SCHOOLS_USERS where school_id=?1 and USERS_id=?2")
    void deleteUserById(Long schoolId, Long userId);
}
