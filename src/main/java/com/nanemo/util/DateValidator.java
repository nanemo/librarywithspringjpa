package com.nanemo.util;

import com.nanemo.entity.Book;
import com.nanemo.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class DateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String birthday = "";
        if (target instanceof Person) {
            Person person = (Person) target;
            birthday = person.getBirthday().trim();
        } else if (target instanceof Book){
            Book book = (Book) target;
            birthday = book.getBirthday().trim();
        }


        if (birthday.isBlank() || birthday.isEmpty()) {
            errors.rejectValue("birthday", "", "Year of date can not be empty");
        } else if (Integer.parseInt(birthday) >= LocalDate.now().getYear()) {
            errors.rejectValue("birthday", "", "Added year cannot be higher than the current year");
        } else if (Integer.parseInt(birthday) < 0) {
            errors.rejectValue("birthday", "", "Added year cannot be negative");
        }

    }

}
