package com.nanemo.repositories.springsparepositories;

import com.nanemo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositorySpringJPA extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderByBookIdAsc();

    List<Book> findByTitleStartingWith(String title);
}
