package com.maksatkyrgyzbaev.ikitep.repository;

import com.maksatkyrgyzbaev.ikitep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username,Long id);

    @Query(value = "select password from users where id = :id ",nativeQuery = true)
    String getPasswordById(@Param("id") Long id);

    @Query(nativeQuery = true,value = "SELECT full_name FROM USERS where school_id = :id")
    List<String> getAllFullNameBySchoolId(@Param("id") Long id);

    User getByFullName(String fullName);

    @Modifying
    @Query(nativeQuery = true,value = "delete FROM USERS_BOOKED_BOOKS  where user_id=?1 and BOOKED_BOOKS_id=?2")
    void deleteBookedBookById(Long userId, Long bookedBookId);
}
