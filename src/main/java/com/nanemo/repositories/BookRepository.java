package com.nanemo.repositories;

import com.nanemo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderByBookIdAsc();

    List<Book> findByTitleStartingWith(String title);
}
