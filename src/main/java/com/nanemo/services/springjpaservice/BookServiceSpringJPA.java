package com.nanemo.services.springjpaservice;

import com.nanemo.entities.Book;
import com.nanemo.repositories.springsparepositories.BookRepositorySpringJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServiceSpringJPA {
    private final BookRepositorySpringJPA bookRepository;


    @Autowired
    public BookServiceSpringJPA(BookRepositorySpringJPA bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAllByOrderByBookIdAsc();
    }

    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book book, Integer id) {
        Optional<Book> bookById = bookRepository.findById(id);


        if (bookById.isPresent()) {
            book.setBookId(id);
            bookRepository.save(book);
        }
    }

    public void deleteBook(Integer id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        }
    }

}
