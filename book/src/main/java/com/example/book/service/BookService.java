package com.example.book.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.book.exception.AlreadyExistsException;
import com.example.book.model.Book;
import com.example.book.repository.BookRepository;
import com.example.book.request.CreateBookDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    @Override
    public String createBook(CreateBookDTO request) {
        if (existsByIsbn(request.getIsbn())) {
            throw new AlreadyExistsException("This book already exists");
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publishedDate(request.getPublishedDate())
                .build();

        bookRepository.save(book);

        return "Book added successfully";

    }

    private boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public String updateBookById(CreateBookDTO request, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find book in database"));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());

        bookRepository.save(book);

        return "You have updated book";
    }

    @Override
    public Page<Book> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    @Override
    public String deleteBookById(Long id) {
        if (!existById(id)) {
            throw new AlreadyExistsException("This book does not exist");
        }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeErrorException(null, "Could not find book by Id"));
        bookRepository.delete(book);
        return "Deleted";
    }

    private boolean existById(Long id) {
        return bookRepository.existsById(id);
    }


    @Override
    public List<Book> findByTitle(String title) {
        List<Book> books = bookRepository.findByTitle(title);

        if (books.isEmpty()) {
            throw new RuntimeException("No books found");
        }

        return books;
    }

}
