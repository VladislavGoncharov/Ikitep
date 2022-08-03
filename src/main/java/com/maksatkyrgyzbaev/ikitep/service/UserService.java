package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO findUserByUsername(String username);

    List<UserDTO> findAll();

    Long getCountUsers();

    UserDTO getById(Long id);

    List<String> getAllFullNameBySchoolId(Long id);

    void save(UserDTO userDTO) throws ValidationException;

    void update(UserDTO userDTO) throws ValidationException;

    void deleteById(Long id);
}
