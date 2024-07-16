package com.SpringBootJDBC.SpringBootJDBC.controllers;


import com.SpringBootJDBC.SpringBootJDBC.models.Book;
import com.SpringBootJDBC.SpringBootJDBC.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

@Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }


    @GetMapping("")
    public ResponseEntity findAll() {
                return new ResponseEntity<>(booksService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(booksService.findById(id), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity create(@RequestBody Book book) {
           booksService.save(book);
            return new ResponseEntity<>("Все прошло хорошо кннига создана", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Book book) {
        booksService.update(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        booksService.deleteById(id);
        return new ResponseEntity<>("Книга удаленна",HttpStatus.LOOP_DETECTED);
    }

}
