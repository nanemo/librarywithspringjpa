package com.nanemo.services.springjpaservice;

import com.nanemo.entities.Book;
import com.nanemo.entities.Person;
import com.nanemo.repositories.springsparepositories.PersonRepositorySpringJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PersonServiceSpringJPA {
    private final PersonRepositorySpringJPA personRepository;

    @Autowired
    public PersonServiceSpringJPA(PersonRepositorySpringJPA personRepository) {
        this.personRepository = personRepository;
    }

    public void addBookToPersonBalance(Integer personId, Integer bookId) {

        Optional<Book> bookById = bookRepository.findById(bookId);
        Optional<Person> personById = personRepository.findById(personId);
        if (bookById.isPresent() && personById.isPresent() && Objects.isNull(bookById.get().getPersonName())){
            personById.get().setBookList();
        }

        bookRepository.addBookToPersonBalance(personId, bookId);
    }

    public void deleteBookFromPersonList(Integer bookId) {
        bookRepository.deleteBookFromPersonList(bookId);
    }

}
