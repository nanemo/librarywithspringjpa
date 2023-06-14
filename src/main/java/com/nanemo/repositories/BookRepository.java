package com.nanemo.repositories;

import com.nanemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository implements AbstractRepository<Book> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT b.book_id, b.book_name, b.author_name, b.birthday, p.name FROM book b " +
                "LEFT JOIN person p on p.person_id = b.person_id ORDER BY b.book_id", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book getById(Integer id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO Book (book_name, author_name, birthday) VALUES (?,?,?)", book.getBookName(), book.getAuthorName(), book.getBirthday());
    }

    @Override
    public void update(Book book, Integer id) {
        jdbcTemplate.update("UPDATE Book SET book_name=?, author_name=?, birthday=? WHERE book_id=?",
                book.getBookName(),
                book.getAuthorName(),
                book.getBirthday(),
                id);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void addBookToPersonBalance(Integer personId, Integer bookId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", personId, bookId);
    }

    public void deleteBookFromPersonList(Integer bookId) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE book_id=?", bookId);
    }
}
