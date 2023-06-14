package com.nanemo.service.springjpaservice;

import com.nanemo.repository.springsparepository.PersonRepositorySpringJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceSpringJPA {
    private final PersonRepositorySpringJPA personRepository;

    @Autowired
    public PersonServiceSpringJPA(PersonRepositorySpringJPA personRepository) {
        this.personRepository = personRepository;
    }
}
