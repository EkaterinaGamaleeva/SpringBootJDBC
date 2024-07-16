package com.SpringBootJDBC.SpringBootJDBC.controllers;

import com.SpringBootJDBC.SpringBootJDBC.models.Book;
import com.SpringBootJDBC.SpringBootJDBC.services.BooksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import org.aspectj.lang.annotation.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class BooksControllerTest {
    @Mock
private BooksService booksService;
    @InjectMocks
private BooksController controller;
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("books")
            .withUsername("postgres")
            .withPassword("postgres");

    @Test
    public void create_StatusCreate() {
        Book book =new Book("jlknlknlk",1985,"jkbnkjnkjn");
        var response=controller.create(book);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    public void findAll_getBooks() {
        Book book =new Book("jlknlknlk",1985,"jkbnkjnkjn");
        Book book2 =new Book("jlknlknlk",1985,"jkbnkjnkjn");
        Book book3 =new Book("jlknlknlk",1985,"jkbnkjnkjn");
        List<Book> bookList=new ArrayList<>();
        bookList.add(book);
        bookList.add(book2);
        bookList.add(book3);
        Mockito.when(booksService.findAll()).thenReturn(bookList);
        var response=controller.findAll();
        assertEquals(bookList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void findOne_getBook() {
        Book book =new Book(10L,"jlknlknlk",1985,"jkbnkjnkjn");
        Mockito.when(booksService.findById(10L)).thenReturn(book);
        var response=controller.findOne(10L);
        assertEquals(book, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void delete_HttpStatus_LOOP_DETECTED() {
        Book book =new Book(10L,"jlknlknlk",1985,"jkbnkjnkjn");
        var response=controller.delete(10L);
        assertEquals(HttpStatus.LOOP_DETECTED, response.getStatusCode());
    }
    @Test
    public void update_HttpStatus_LOOP_DETECTED() {
        Book book =new Book(10L,"jlknlknlk",1985,"jkbnkjnkjn");
        Book book2 =new Book("jlknlknlk",1990,"jkbnkjnkjn");
        var response=controller.update(10L,book2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}