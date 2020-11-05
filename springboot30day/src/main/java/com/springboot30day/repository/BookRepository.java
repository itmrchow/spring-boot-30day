package com.springboot30day.repository;

import com.springboot30day.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Prevents DELETE /books/:id
    @Override
    @RestResource(exported = false)
    void delete(Book t);

}
