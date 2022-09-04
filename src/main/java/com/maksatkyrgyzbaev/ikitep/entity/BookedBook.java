package com.maksatkyrgyzbaev.ikitep.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "booked_books")
public class BookedBook {
    private static final String SEQ_NAME = "booked_books_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User user;
    private LocalDate dateOfCreation;
    private LocalDate returnDate;
    private boolean bookingIsActive;

    @PrePersist
    public void prePersist() {
        dateOfCreation = LocalDate.now();
        bookingIsActive = true;
    }

}
