package com.nanemo.service;

import com.nanemo.entity.Book;
import com.nanemo.entity.Person;
import com.nanemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPeople() {
        return personRepository.getAll();
    }

    public Person getPersonById(Integer personId) {
        return personRepository.getById(personId);
    }

    public void createPerson(Person person) {
        personRepository.create(person);
    }

    public void updatePerson(Person person, Integer personId) {
        Person trimmedPerson = trimPersonFields(person);
        personRepository.update(trimmedPerson, personId);
    }

    private Person trimPersonFields(Person person) {
        Person newPerson = new Person();
        newPerson.setPersonId(person.getPersonId());
        newPerson.setName(person.getName().trim());
        newPerson.setBirthday(person.getBirthday().trim());
        return newPerson;
    }

    public void deletePerson(Integer personId) {
        personRepository.delete(personId);
    }

    public Person getPersonWithOrderedBookList(Integer personId) {
        Person person = personRepository.getById(personId);
        person.setBookList(personRepository.getPersonOrderedBookList(personId));

        return person;
    }

    public List<Book> getFreeBookLists(Integer personId) {
        return personRepository.getFreeBookLists(personId);
    }

    public Optional<Person> getPersonByName(String name) {
        return personRepository.getPersonByName(name);
    }


}
