package com.nanemo.repositories.springsparepositories;

import com.nanemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositorySpringJPA extends JpaRepository<Book, Integer> {
}
