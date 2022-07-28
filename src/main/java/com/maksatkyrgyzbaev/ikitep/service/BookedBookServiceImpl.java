package com.maksatkyrgyzbaev.ikitep.service;

import com.maksatkyrgyzbaev.ikitep.repository.BookedBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookedBookServiceImpl implements BookedBookService {

    private final BookedBookRepository bookedBookRepository;

    public BookedBookServiceImpl(BookedBookRepository bookedBookRepository) {
        this.bookedBookRepository = bookedBookRepository;
    }
}
