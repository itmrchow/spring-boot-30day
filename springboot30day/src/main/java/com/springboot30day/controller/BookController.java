package com.springboot30day.controller;

import java.util.List;

import com.springboot30day.dto.BookDto;
import com.springboot30day.entity.Book;
import com.springboot30day.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Book")
@RestController
@RequestMapping(value = "/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @ApiOperation(value = "取得書本", notes = "列出所有書本")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "v1/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @ApiOperation(value = "新增書本", notes = "新增書本內容")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "存檔成功") })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "v1/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto create(@ApiParam(required = true, value = "書本內容") @RequestBody BookDto bookDto) {
        Book book = new Book();
        book.setBookid(bookDto.getBookid());
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());

        book = bookRepository.save(book);
        bookDto.setBookid(book.getBookid());
        return bookDto;
    }

    @ApiOperation(value = "取得書本內容", notes = "取得書本內容")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "書本資訊") })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/book/{bookid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto get(@ApiParam(required = true, name = "bookid", value = "書本ID") @PathVariable Integer bookid) {
        Book book = bookRepository.getOne(bookid);
        BookDto bookDto = new BookDto();
        bookDto.setBookid(book.getBookid());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor());
        return bookDto;
    }

}
