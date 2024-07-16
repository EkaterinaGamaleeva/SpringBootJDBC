package com.SpringBootJDBC.SpringBootJDBC.repositories;



import com.SpringBootJDBC.SpringBootJDBC.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface BooksRepository {
    void save(Book book);
    void update(Long id,Book book);

    void deleteById(Long id);

     List<Book> findAll();

     Book findById(Long id);

}
