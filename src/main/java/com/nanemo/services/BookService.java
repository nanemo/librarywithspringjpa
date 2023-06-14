package com.nanemo.services;

import com.nanemo.entities.Book;
import com.nanemo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.getAll();
    }

    public Book getBookById(Integer id){
        return bookRepository.getById(id);
    }

    public void createBook(Book book){
        bookRepository.create(book);
    }

    public void updateBook(Book book, Integer id){
        bookRepository.update(book, id);
    }

    public void deleteBook(Integer id){
        bookRepository.delete(id);
    }

    public void addBookToPersonBalance(Integer personId, Integer bookId) {
        bookRepository.addBookToPersonBalance(personId, bookId);
    }

    public void deleteBookFromPersonList(Integer bookId) {
        bookRepository.deleteBookFromPersonList(bookId);
    }
}
