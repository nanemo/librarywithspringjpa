package com.nanemo.repository;

import com.nanemo.entity.Book;
import com.nanemo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository implements AbstractRepository<Person> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person p ORDER BY p.person_id", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person getById(Integer id) {
        return jdbcTemplate.query("SELECT p.* FROM Person p WHERE p.person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    @Override
    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, birthday) VALUES (?, ?)", person.getName(), person.getBirthday());
    }

    @Override
    public void update(Person person, Integer person_id) {
        jdbcTemplate.update("UPDATE Person SET name=?, birthday=? WHERE person_id=?",
                person.getName(),
                person.getBirthday(),
                person_id);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM Person p WHERE p.person_id=?", id);
    }

    public List<Book> getPersonOrderedBookList(Integer personId) {
        return jdbcTemplate.query("SELECT b.* FROM Book b WHERE b.person_id=?",
                new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }


    public List<Book> getFreeBookLists(Integer personId) {
        return jdbcTemplate.query("SELECT * FROM Book b WHERE b.person_id IS NULL",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getPersonByName(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
