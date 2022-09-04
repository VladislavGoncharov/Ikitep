package com.maksatkyrgyzbaev.ikitep.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {
    private static final String SEQ_NAME = "user_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String fullName;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
    @OneToMany
    private List<BookedBook> bookedBooks;

    @PrePersist
    public void prePersist() {
        bookedBooks = new ArrayList<>();
    }
}
