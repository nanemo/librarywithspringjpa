package com.nanemo.repository.springsparepository;

import com.nanemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositorySpringJPA extends JpaRepository<Person, Integer> {
}
