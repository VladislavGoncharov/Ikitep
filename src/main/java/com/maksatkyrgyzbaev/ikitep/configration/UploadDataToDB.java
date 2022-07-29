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
                    .schoolImg("school_1.png")
                    .build());
            schoolRepository.save(School.builder()
                    .schoolName("школа№2")
                    .schoolImg("school_1.png")
                    .build());
            schoolRepository.save(School.builder()
                    .schoolName("школа№3")
                    .schoolImg("school_1.png")
                    .build());

            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Сергей Александрович")
                    .role(Role.ADMIN)
                    .school(schoolRepository.getById(1L))
                    .build());
            userRepository.save(User.builder()
                    .username("librarian1")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Людмила Степанова")
                    .role(Role.LIBRARIAN)
                    .school(schoolRepository.getById(1L))
                    .build());
            userRepository.save(User.builder()
                    .username("librarian2")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Ирина Романовна")
                    .role(Role.LIBRARIAN)
                    .school(schoolRepository.getById(2L))
                    .build());
            userRepository.save(User.builder()
                    .username("librarian3")
                    .password(passwordEncoder.encode("123"))
                    .fullName("Лариса Петровна")
                    .role(Role.LIBRARIAN)
                    .school(schoolRepository.getById(3L))
                    .build());
        };
    }
}
