package com.nanemo.services.springjpaservice;

import com.nanemo.entities.Book;
import com.nanemo.repositories.springsparepositories.BookRepositorySpringJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServiceSpringJPA {
    private static final String BAD_REQUEST_MESSAGE = "Given ID is invalid: ";
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
        if (isNewBookEmpty(book)
                || book.getTitle().isEmpty()
                || book.getAuthor().isEmpty()
                || book.getTakenAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
        }
        bookRepository.save(book);
    }

    private boolean isNewBookEmpty(Book book) {
        return book.getTitle() == null &&
                book.getAuthor() == null &&
                book.getTakenAt() == null &&
                book.getPersonName() == null;
    }

    public void updateBook(Book newBook, Integer id) {
        Optional<Book> bookById = bookRepository.findById(id);
        bookById.ifPresent(bookFromData -> {
            bookFromData.setTitle(newBook.getTitle() != null ? newBook.getTitle() : bookFromData.getTitle());
            bookFromData.setAuthor(newBook.getAuthor() != null ? newBook.getAuthor() : bookFromData.getAuthor());
            bookFromData.setTakenAt(newBook.getTakenAt() != null ? newBook.getTakenAt() : bookFromData.getTakenAt());

            bookRepository.save(bookFromData);
        });

    }

    public void deleteBook(Integer id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        }
    }

}
