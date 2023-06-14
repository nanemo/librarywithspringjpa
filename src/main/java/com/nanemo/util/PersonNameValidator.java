package com.nanemo.util;

import com.nanemo.entity.Person;
import com.nanemo.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonNameValidator implements Validator {

    private final PersonService personService;

    public PersonNameValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personService.getPersonByName(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "This person has already exist");
        }

    }


}
