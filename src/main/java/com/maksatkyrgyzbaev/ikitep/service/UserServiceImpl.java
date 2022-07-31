package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.dto.UserDTO;
import com.maksatkyrgyzbaev.ikitep.entity.BookedBook;
import com.maksatkyrgyzbaev.ikitep.entity.Role;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import com.maksatkyrgyzbaev.ikitep.mapper.UserMapper;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import com.maksatkyrgyzbaev.ikitep.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final SchoolRepository schoolRepository;
    private final BookedBookService bookedBookService;

    public UserServiceImpl(UserRepository userRepository, SchoolRepository schoolRepository, BookedBookService bookedBookService) {
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.bookedBookService = bookedBookService;
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
        User user = userRepository.getById(id);

        if (user.getBookedBooks().size() > 0) {
            for (BookedBook bookedBook : user.getBookedBooks()) {
                bookedBookService.deleteById(bookedBook.getId());
            }
        }
        schoolRepository.deleteUserById(user.getSchool().getId(), id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.getById(id);
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .matchingPassword(user.getPassword())
                .fullName(user.getFullName())
                .role(user.getRole())
                .schoolName(user.getSchool().getSchoolName())
                .build();
    }


    @Override
    public void save(UserDTO userDTO) throws ValidationException {
        if (userRepository.existsByUsername(userDTO.getUsername()))
            throw new ValidationException("Пользователь с таким именем уже существует");

        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())
                || userDTO.getPassword().isEmpty() || userDTO.getPassword() == null)
            throw new ValidationException("Пароли не совпадают");


        if (!Role.isRole(userDTO.getRole()))
            throw new ValidationException("Роль не выбрана");

        if (!schoolRepository.existsBySchoolName(userDTO.getSchoolName()))
            throw new ValidationException("Школа не выбрана");

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .fullName(userDTO.getFullName())
                .role(userDTO.getRole())
                .school(schoolRepository.getBySchoolName(userDTO.getSchoolName()))
                .build();
        School school = schoolRepository.getBySchoolName(userDTO.getSchoolName());
        school.getUsers().add(user);
        schoolRepository.save(school);
    }

    @Override
    public void update(UserDTO userDTO) throws ValidationException {
        User oldUser = userRepository.getById(userDTO.getId());

        if (userRepository.existsByUsernameAndIdNot(userDTO.getUsername(), userDTO.getId()))
            throw new ValidationException("Пользователь с таким именем уже существует");

        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())
                || userDTO.getPassword().isEmpty() || userDTO.getPassword() == null)
            throw new ValidationException("Пароли не совпадают");

        // Если пароль сменили, его нужно вновь закодировать
        if (!Objects.equals(userDTO.getPassword(), oldUser.getPassword()))
            userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

        if (!Role.isRole(userDTO.getRole()))
            throw new ValidationException("Роль не выбрана");
        if (!schoolRepository.existsBySchoolName(userDTO.getSchoolName()))
            throw new ValidationException("Школа не выбрана");

        User updateUser = User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .fullName(userDTO.getFullName())
                .role(userDTO.getRole())
                .school(oldUser.getSchool())
                .bookedBooks(oldUser.getBookedBooks())
                .build();

        // Если школа изменилась, из старой удаляем, в новую заносим
        if (!oldUser.getSchool().getSchoolName().equals(userDTO.getSchoolName())) {
            schoolRepository.deleteUserById(oldUser.getSchool().getId(), oldUser.getId());

            School newSchool = schoolRepository.getBySchoolName(userDTO.getSchoolName());
            newSchool.getUsers().add(updateUser);
            schoolRepository.save(newSchool);

            // Удаляем всю историю взятия книг со старой школы
            if (oldUser.getBookedBooks().size() > 0) {
                for (BookedBook bookedBook : oldUser.getBookedBooks()) {
                    bookedBookService.deleteById(bookedBook.getId());
                }
            }
        } else userRepository.save(updateUser);
    }

    @Override
    public List<String> getAllFullNameBySchoolId(Long id) {
        return userRepository.getAllFullNameBySchoolId(id);
    }
}
