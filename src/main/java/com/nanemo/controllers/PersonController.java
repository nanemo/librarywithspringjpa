package com.nanemo.controllers;

import com.nanemo.entities.Person;
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

    @Autowired
    public PersonController(PersonService personService, PersonNameValidator personNameValidator) {
        this.personService = personService;
        this.personNameValidator = personNameValidator;
    }

    @GetMapping("/all")
    public String getPeople(Model model) {
        model.addAttribute("people", personService.findAll());
        return "person/person_list";
    }

    @GetMapping("/{person_id}")
    public String listOfOrderedBooks(Model model,
                                     @PathVariable("person_id") Integer personId) {
        model.addAttribute("person", personService.findById(personId));
        model.addAttribute("books", personService.getBooksByPersonId(personId));

        return "person/persons_ordered_books";
    }

    @GetMapping("/before_create")
    public String beforeCreate(@ModelAttribute Person person) {
        return "person/create_person";
    }

    @PostMapping("/create")
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personNameValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "person/create_person";
        }

        personService.save(person);
        return "redirect:/person/all";
    }

    @GetMapping("/before_update/{person_id}")
    public String beforeUpdate(Model model,
                               @PathVariable("person_id") Integer personId) {
        model.addAttribute("person", personService.findById(personId));
        return "person/update_person";
    }

    @PostMapping("/update/{person_id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("person_id") Integer personId) {
        person.setPersonId(personId);

        if (!person.getName().equals(personService.findById(personId).getName())) {
            personNameValidator.validate(person, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return "person/update_person";
        }

        personService.update(personId, person);
        return "redirect:/person/all";
    }

    @DeleteMapping("/delete/{person_id}")
    public String deletePerson(@PathVariable("person_id") Integer personId) {
        personService.delete(personId);
        return "redirect:/person/all";
    }

}
