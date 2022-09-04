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

    boolean existsBySchoolNameAndIdNot(String schoolName,Long id);
    boolean existsBySchoolName(String schoolName);

    School getBySchoolName(String schoolName);

    @Query(nativeQuery = true,value = "select school_name from schools where id = ?1")
    String getSchoolNameById(Long id);

    @Query(nativeQuery = true, value =
            "select s.school_name,s.school_img from schools s where s.id = :id")
    String getSchoolWithNameAndImgById(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select id, school_name from schools")
    List<Object[]> findAllIdAndSchoolName();

    @Modifying
    @Query(nativeQuery = true,value = "insert into SCHOOLS_BOOKS values(?1,?2)")
    void saveBookInSchool(Long schoolId, Long bookId);

    @Modifying
    @Query(nativeQuery = true,value = "insert into SCHOOLS_USERS values(?1,?2)")
    void saveUserInSchool(Long schoolId, Long id);

    @Modifying
    @Query(nativeQuery = true,value = "delete FROM SCHOOLS_BOOKS where school_id=?1 and books_id=?2")
    void deleteBookByIdFromSchool(Long schoolId, Long bookId);

    @Modifying
    @Query(nativeQuery = true,value = "delete FROM SCHOOLS_BOOKED_BOOKS  where school_id=?1 and BOOKED_BOOKS_id=?2")
    void deleteBookedBookById(Long schoolId, Long bookedBookId);

    @Modifying
    @Query(nativeQuery = true,value = "delete FROM SCHOOLS_USERS where school_id=?1 and USERS_id=?2")
    void deleteUserById(Long schoolId, Long userId);
}
