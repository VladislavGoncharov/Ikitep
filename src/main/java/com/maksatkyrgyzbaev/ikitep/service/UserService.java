package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO findUserByUsername(String username);

    Long getCountUsers();

    List<UserDTO> findAll();

    void deleteById(Long id);

    UserDTO getById(Long id);

    void update(UserDTO userDTO) throws ValidationException;

    void save(UserDTO userDTO) throws ValidationException;
}
