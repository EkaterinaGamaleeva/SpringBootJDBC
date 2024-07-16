package com.SpringBootJDBC.SpringBootJDBC.models;


import lombok.Data;

import java.io.Serializable;
@Data
public class Book implements Serializable {

    private Long id;
    private String title;
    private int publicationYear;
    private String author;

    public Book(Long id, String title, int ublicationYear, String author) {
        this.id=id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    public Book(String title, int publicationYear, String author) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
    }

}