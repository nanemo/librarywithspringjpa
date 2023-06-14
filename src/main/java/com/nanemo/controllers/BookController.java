package com.nanemo.controllers;

import com.nanemo.entities.Book;
import com.nanemo.entities.Person;
import com.nanemo.services.BookService;
import com.nanemo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService,
                          PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping("/all")
    public String getAllBooks(Model model,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                              @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {
        if (page == null || booksPerPage == null)
            model.addAttribute("books", bookService.findAll(sortByYear));
        else model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByYear));

        return "book/book_list";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer bookId,
                              Model model,
                              @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findBookById(bookId));

        Person bookOwner = bookService.getBookOwner(bookId);

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", personService.findAll());

        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") @Valid Book book) {
        return "book/new";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        bookService.saveBook(book);
        return "redirect:/book/all";
    }

    @GetMapping("/before_update/{book_id}")
    public String beforeUpdateBook(Model model,
                                   @PathVariable("book_id") Integer bookId) {
        model.addAttribute("book", bookService.findBookById(bookId));
        return "book/update";
    }

    @PostMapping("/update/{book_id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("book_id") Integer bookId) {

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

    @PatchMapping("/release/{id}")
    public String release(@PathVariable("id") Integer bookId) {
        bookService.release(bookId);

        return "redirect:/book/" + bookId;
    }

    @PatchMapping("/assign/{id}")
    public String assign(@PathVariable("id") Integer bookId, @ModelAttribute("person") Person selectedPerson) {
        // For selectedPerson init just id another fields are null
        bookService.assign(bookId, selectedPerson);

        return "redirect:/book/" + bookId;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "book/search";
    }

}
