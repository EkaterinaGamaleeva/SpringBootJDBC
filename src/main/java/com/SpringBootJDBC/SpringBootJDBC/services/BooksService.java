package com.SpringBootJDBC.SpringBootJDBC.services;
import com.SpringBootJDBC.SpringBootJDBC.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.SpringBootJDBC.SpringBootJDBC.repositories.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BooksService implements BooksRepository {
private final  JdbcTemplate jdbcTemplate;

@Autowired
    public BooksService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, publicationYear, author) VALUES(?,?,?)",book.getTitle(), book.getPublicationYear(),book.getAuthor());

}

    @Override
    public void update(Long id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title=?, publicationYear=?, author=? WHERE id=?",new Object[] {book.getTitle(),
                book.getPublicationYear(),book.getAuthor()}, id);
    }

    @Override
    public void deleteById(Long id) {
         jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    @Override
    public List<Book> findAll() {
     return  jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
}
