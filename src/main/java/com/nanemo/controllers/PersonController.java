package com.nanemo.controllers;

import com.nanemo.entity.Person;
    import com.nanemo.services.PersonService;
import com.nanemo.util.DateValidator;
import com.nanemo.util.PersonNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    private final PersonNameValidator personNameValidator;
    private final DateValidator dateValidator;

    @Autowired
    public PersonController(PersonService personService, PersonNameValidator personNameValidator, DateValidator dateValidator) {
        this.personService = personService;
        this.personNameValidator = personNameValidator;
        this.dateValidator = dateValidator;
    }

    @GetMapping("/all")
    public String getPeople(Model model) {
        model.addAttribute("people", personService.getPeople());
        return "person/person_list";
    }

    @GetMapping("/ordered_book/{person_id}")
    public String listOfOrderedBooks(Model model,
                                     @PathVariable("person_id") Integer personId) {
        model.addAttribute("person", personService.getPersonWithOrderedBookList(personId));
        return "person/persons_ordered_books";
    }

    @GetMapping("/free_book/{person_id}")
    public String getFreeBookLists(Model model,
                                   @PathVariable("person_id") Integer personId) {
        model.addAttribute("free_books", personService.getFreeBookLists(personId));
        model.addAttribute("current_person_id", personId);

        return "book/free_books";
    }

    @GetMapping("/before_create")
    public String beforeCreate(Model model) {
        model.addAttribute("person", new Person());
        return "person/create_person";
    }

    @PostMapping("/create")
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personNameValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "person/create_person";
        }

        personService.createPerson(person);
        return "redirect:/person/all";
    }

    @GetMapping("/before_update/{person_id}")
    public String beforeUpdate(Model model,
                               @PathVariable("person_id") Integer personId) {
        model.addAttribute("person", personService.getPersonById(personId));
        return "person/update_person";
    }

    @PostMapping("/update/{person_id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("person_id") Integer personId) {
        person.setPersonId(personId);

        if (!person.getName().equals(personService.getPersonById(personId).getName())) {
            personNameValidator.validate(person, bindingResult);
        }

        dateValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "person/update_person";
        }

        personService.updatePerson(person, personId);
        return "redirect:/person/all";
    }

    @DeleteMapping("/delete/{person_id}")
    public String deletePerson(@PathVariable("person_id") Integer personId) {
        personService.deletePerson(personId);
        return "redirect:/person/all";
    }

}
