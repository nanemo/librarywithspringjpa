package com.nanemo.services;

import com.nanemo.entities.Book;
import com.nanemo.entities.Person;
import com.nanemo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(Boolean sortByYear) {
        if (sortByYear) return bookRepository.findAll(Sort.by("year"));
        else return bookRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, Boolean sortByYear) {
        if (sortByYear) return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> searchByTitle(String query) {
        return bookRepository.findByTitleStartingWith(query);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Book newBook, Integer id) {
        Book bookById = bookRepository.findById(id).get();

        newBook.setBookId(id);
        newBook.setOwner(bookById.getOwner()); // Not to lose connection on updating book

        bookRepository.save(newBook);
    }

    @Transactional
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public Person getBookOwner(Integer personId) {
        return bookRepository.findById(personId).map(Book::getOwner).orElse(null);
    }

    // For giving back book to library
    @Transactional
    public void release(Integer id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setTakenAt(null);
        });
    }

    // For taking book from library to read
    @Transactional
    public void assign(Integer bookId, Person selectedPerson) {
        bookRepository.findById(bookId).ifPresent(book -> {
            book.setOwner(selectedPerson);
            book.setTakenAt(new Date());
        });
    }

}
