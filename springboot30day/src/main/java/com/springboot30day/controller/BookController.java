package com.springboot30day.controller;

import java.util.List;

import com.springboot30day.entity.Book;
import com.springboot30day.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "v1/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

}
