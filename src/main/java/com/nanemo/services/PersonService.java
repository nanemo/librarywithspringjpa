package com.nanemo.services;

import com.nanemo.entities.Book;
import com.nanemo.entities.Person;
import com.nanemo.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Integer personId) {
        Optional<Person> personById = personRepository.findById(personId);
        return personById.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(Integer personId, Person newPerson) {
        newPerson.setPersonId(personId);
        personRepository.save(newPerson);
    }

    @Transactional
    public void delete(Integer personId) {
        personRepository.deleteById(personId);
    }

    public Optional<Person> getPersonByName(String name) {
        return personRepository.findByName(name);
    }

    public List<Book> getBooksByPersonId(Integer personId) {
        Optional<Person> personById = personRepository.findById(personId);

        if (personById.isPresent()) {
            Hibernate.initialize(personById.get().getBookList());
            personById.get().getBookList().forEach(book -> {
                long diffInMil = Math.abs(book.getTakenAt().getTime() - new Date().getTime());

                if (diffInMil > 864000000) {
                    book.setExpired(true);
                }
            });
            return personById.get().getBookList();
        } else {
            return Collections.emptyList();
        }
    }




//    public void addBookToPersonBalance(Integer personId, Integer bookId) {
//
//        Optional<Book> bookById = bookRepository.findById(bookId);
//        Optional<Person> personById = personRepository.findById(personId);
//        if (bookById.isPresent() && personById.isPresent() && Objects.isNull(bookById.get().getPersonName())) {
//            personById.get().setBookList();
//        }
//
//        bookRepository.addBookToPersonBalance(personId, bookId);
//    }
//
//    public void deleteBookFromPersonList(Integer bookId) {
//        bookRepository.deleteBookFromPersonList(bookId);
//    }

}
