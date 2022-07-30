package com.maksatkyrgyzbaev.ikitep.repository;

import com.maksatkyrgyzbaev.ikitep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username,Long id);

    @Query(value = "select password from users where id = :id ",nativeQuery = true)
    String getPasswordById(@Param("id") Long id);
}
