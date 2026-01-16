package com.example.book.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.example.book.model.Book;
import com.example.book.request.CreateBookDTO;

public interface IBookService {

    public Page<Book> getAllBooks(int page, int size);
    public String createBook(CreateBookDTO request);
    public String updateBookById(CreateBookDTO request, Long id);
    public String deleteBookById(Long id);
    List<Book> findByTitle(String title);

    
}
