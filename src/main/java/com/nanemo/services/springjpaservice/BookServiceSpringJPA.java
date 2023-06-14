package com.nanemo.services.springjpaservice;

import com.nanemo.repositories.springsparepositories.BookRepositorySpringJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceSpringJPA {
    private final BookRepositorySpringJPA bookRepository;

    @Autowired
    public BookServiceSpringJPA(BookRepositorySpringJPA bookRepository) {
        this.bookRepository = bookRepository;
    }


}
