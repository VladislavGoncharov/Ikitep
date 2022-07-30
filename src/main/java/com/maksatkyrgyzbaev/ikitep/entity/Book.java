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
@Table(name = "books")
public class Book {
    private static final String SEQ_NAME = "books_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private String author;
    private String bookName;
    private Long serialNumber; //для сканера штрих кода!!!!
    private int like; // смотреть как часто его беруть то и есть можем ввыводить топ книг!!!!
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
    @OneToMany
    private List<BookedBook> bookedBook;
    private int totalNumber;
    private int remains;



}
