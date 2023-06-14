package com.nanemo.controllers;

import com.nanemo.entity.Book;
import com.nanemo.services.BookService;
import com.nanemo.services.PersonService;
import com.nanemo.util.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;
    private final DateValidator dateValidator;

    @Autowired
    public BookController(BookService bookService,
                          PersonService personService,
                          DateValidator dateValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.dateValidator = dateValidator;
    }

    @GetMapping("/all")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book/book_list";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/new";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        dateValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        bookService.createBook(book);
        return "redirect:/book/all";
    }

    @PostMapping("/add_book_to_person/{person_id}/{book_id}")
    public String addBookToPersonBalance(@PathVariable("person_id") Integer personId,
                                         @PathVariable("book_id") Integer bookId) {
        bookService.addBookToPersonBalance(personId, bookId);
        return "redirect:/person/free_book/" + personId;
    }

    @PostMapping("/delete_book_from_person_list/{person_id}/{book_id}")
    public String deleteBookFromPersonList(ModelMap modelMap,
                                           @PathVariable("person_id") Integer personId,
                                           @PathVariable("book_id") Integer bookId) {
        bookService.deleteBookFromPersonList(bookId);
        modelMap.addAttribute("person", personService.getPersonWithOrderedBookList(personId));
        return "person/persons_ordered_books";
    }

    @GetMapping("/before_update/{book_id}")
    public String beforeUpdateBook(Model model,
                                   @PathVariable("book_id") Integer bookId) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "book/update";
    }

    @PostMapping("/update/{book_id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("book_id") Integer bookId) {
        dateValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/update";
        }

        bookService.updateBook(book, bookId);
        return "redirect:/book/all";
    }

    @DeleteMapping("/delete/{book_id}")
    public String deleteBook(@PathVariable("book_id") Integer bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/book/all";
    }

}
