package com.maksatkyrgyzbaev.ikitep.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "schools")
public class School {
    private static final String SEQ_NAME = "schools_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    private String schoolName;
    @OneToMany
    private List<User> users;
    @OneToMany
    private List<Book> books;
    @OneToMany
    private List<BookedBook> bookedBooks;
}
