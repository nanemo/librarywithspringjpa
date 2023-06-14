package com.nanemo.repositories.springsparepositories;

import com.nanemo.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepositorySpringJPA extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}
