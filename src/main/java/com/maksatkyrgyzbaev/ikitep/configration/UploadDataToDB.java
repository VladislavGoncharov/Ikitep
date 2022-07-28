package com.maksatkyrgyzbaev.ikitep.configration;

import com.maksatkyrgyzbaev.ikitep.entity.Role;
import com.maksatkyrgyzbaev.ikitep.entity.School;
import com.maksatkyrgyzbaev.ikitep.entity.User;
import com.maksatkyrgyzbaev.ikitep.repository.SchoolRepository;
import com.maksatkyrgyzbaev.ikitep.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UploadDataToDB {

    private final PasswordEncoder passwordEncoder;

    public UploadDataToDB(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, SchoolRepository schoolRepository) {
        return (args) -> {
            schoolRepository.save(School.builder()
                    .schoolName("школа№1")
                    .build());
            schoolRepository.save(School.builder()
                    .schoolName("школа№2")
                    .build());
            schoolRepository.save(School.builder()
                    .schoolName("школа№3")
                    .build());

            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123"))
                    .role(Role.ADMIN)
                    .school(schoolRepository.getById(1L))
                    .build());
            userRepository.save(User.builder()
                    .username("librarian1")
                    .password(passwordEncoder.encode("123"))
                    .role(Role.LIBRARIAN)
                    .school(schoolRepository.getById(1L))
                    .build());
            userRepository.save(User.builder()
                    .username("librarian2")
                    .password(passwordEncoder.encode("123"))
                    .role(Role.LIBRARIAN)
                    .school(schoolRepository.getById(2L))
                    .build());
            userRepository.save(User.builder()
                    .username("librarian3")
                    .password(passwordEncoder.encode("123"))
                    .role(Role.LIBRARIAN)
                    .school(schoolRepository.getById(3L))
                    .build());
        };
    }
}
