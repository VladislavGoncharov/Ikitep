package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.SchoolDTO;
import com.maksatkyrgyzbaev.ikitep.dto.UserDTO;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import com.maksatkyrgyzbaev.ikitep.mapper.UserMapper;
import com.maksatkyrgyzbaev.ikitep.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper MAPPER = UserMapper.MAPPER;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Такой пользователь не найден: " + username);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return MAPPER.fromUser(userRepository.findUserByUsername(username));
    }

    @Override
    public Long getCountUsers() {
        return userRepository.count();
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .fullName(user.getFullName())
                        .role(user.getRole())
                        .schoolName(user.getSchool().getSchoolName())
                        .countBookedBooksId(user.getBookedBooks().size())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.getById(id);
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .schoolName(user.getSchool().getSchoolName())
                .build();
    }

    @Override
    public void update(UserDTO userDTO) throws ValidationException {

    }

    @Override
    public void save(UserDTO userDTO) throws ValidationException {
        if (userRepository.existsByUsername(userDTO.getUsername()))
            throw new ValidationException("Пользователь с таким именем уже существует");
        if (!Objects.equals(userDTO.getPassword(),userDTO.getMatchingPassword()))
            throw new ValidationException("Пароли не совпадают");



    }

    @Override
    public void save(SchoolDTO schoolDTO) throws ValidationException {
        if (schoolRepository.existsBySchoolName(schoolDTO.getSchoolName()))
            throw new ValidationException("Такая школа уже существует");
        if (schoolDTO.getSchoolImg() == null || schoolDTO.getSchoolImg().isEmpty())
            schoolDTO.setSchoolImg("school_1.png");

        schoolRepository.save(School.builder()
                .schoolName(schoolDTO.getSchoolName())
                .schoolImg(schoolDTO.getSchoolImg())
                .build());
    }

    @Override
    public void update(SchoolDTO schoolDTO) throws ValidationException {
        if (schoolRepository.existsBySchoolNameAndIdNot(schoolDTO.getSchoolName(), schoolDTO.getId()))
            throw new ValidationException("Такая школа уже существует");

        if (schoolDTO.getSchoolImg() == null || schoolDTO.getSchoolImg().isEmpty())
            schoolDTO.setSchoolImg("school_1.png");

        schoolRepository.save(MAPPER.toSchool(schoolDTO, schoolRepository.getById(schoolDTO.getId())));
    }
}
