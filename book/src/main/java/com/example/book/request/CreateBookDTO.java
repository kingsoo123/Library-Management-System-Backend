package com.example.book.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDTO {

     private String title;
    private String author;
    private String isbn;
    private String publishedDate;
    
}
