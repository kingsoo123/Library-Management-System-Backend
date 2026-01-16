package com.example.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

     List<Book> findByTitle(String title);
    
}
