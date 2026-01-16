package com.example.book.controller;

import java.nio.channels.AlreadyBoundException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.exception.AlreadyExistsException;
import com.example.book.model.Book;
import com.example.book.request.CreateBookDTO;
import com.example.book.response.BookResponseDTO;
import com.example.book.service.IBookService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;

    @GetMapping("/books")
    public ResponseEntity<BookResponseDTO> getAllBooks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        try {
            Page<Book> book = bookService.getAllBooks(page, size);
            return ResponseEntity.ok(new BookResponseDTO("All books", book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BookResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/books")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody CreateBookDTO request) {

        try {
            var book = bookService.createBook(request);
            return ResponseEntity.ok(new BookResponseDTO("Added", book));
        } catch (AlreadyBoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BookResponseDTO(e.getMessage(), null));
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody CreateBookDTO request, @PathVariable Long id) {
        try {
            var book = bookService.updateBookById(request, id);
            return ResponseEntity.ok(new BookResponseDTO("updated", book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BookResponseDTO(e.getMessage(), null));
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<BookResponseDTO> deleteById(@PathVariable Long id) {
        try {
            var book = bookService.deleteBookById(id);
            return ResponseEntity.ok(new BookResponseDTO("Book dleted!", book));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BookResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("books/search")

    public ResponseEntity<BookResponseDTO> findByTitle(@RequestParam(required = false) String title) {
        try {
            List<Book> books = bookService.findByTitle(title);
            return ResponseEntity.ok(new BookResponseDTO("Searched successfull", books));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BookResponseDTO("The book is not available", null));
        }
    }

}
