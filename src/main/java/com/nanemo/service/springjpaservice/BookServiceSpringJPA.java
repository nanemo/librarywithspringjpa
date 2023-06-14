package com.nanemo.service.springjpaservice;

import com.nanemo.repository.springsparepository.BookRepositorySpringJPA;
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
