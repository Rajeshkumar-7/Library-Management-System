package com.example.LibraryManagementSystem.controllers;

import com.example.LibraryManagementSystem.dto.responseDTO.BookResponse;
import com.example.LibraryManagementSystem.enums.Genre;
import com.example.LibraryManagementSystem.exceptions.AuthorNotFoundException;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add-book")
    public String addBook(@RequestBody Book book){

        try{
            String response = bookService.addBook(book);
            return response;
        }
        catch(AuthorNotFoundException e){
            return e.getMessage();
        }
    }

    @GetMapping("/get-book-by-sql/{genre}/{cost}")
    public ResponseEntity getBookBySQL(@PathVariable String genre ,@PathVariable double cost){
        List<BookResponse> bookResponses = bookService.getBookBySQL(genre , cost);
        return new ResponseEntity<>(bookResponses , HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-book-by-hql/{genre}/{cost}")
    public ResponseEntity getBookByHQL(@PathVariable Genre genre ,@PathVariable double cost){
        List<BookResponse> bookResponses = bookService.getBookByHQL(genre , cost);
        return new ResponseEntity<>(bookResponses , HttpStatus.ACCEPTED);
    }

    // Delete a book
    @DeleteMapping("/delete-book/{book-id}")
    public ResponseEntity deleteBook(@PathVariable("book-id") int bookId){
        String response = bookService.deleteBook(bookId);
        return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
    }

    // Books of particular genre
    @GetMapping("/get-book-by-genre/{genre}")
    public ResponseEntity getBookByGenre(@PathVariable("genre") Genre genre){
        List<BookResponse> bookResponseList = bookService.getBookByGenre(genre);
        return new ResponseEntity<>(bookResponseList , HttpStatus.ACCEPTED);
    }

    // Books of particular genre and cost lesser than or equal to the given amount
    @GetMapping("/get-book-by-genre-and-cost/{genre}/{cost}")
    public ResponseEntity getBookByGenreAndCost(@PathVariable("genre") Genre genre , @PathVariable("cost") double cost){
        List<BookResponse> bookResponseList = bookService.getBookByGenreAndCost(genre , cost);
        return new ResponseEntity<>(bookResponseList , HttpStatus.ACCEPTED);
    }

    // All the books having number of pages between 'a' and 'b'
    @GetMapping("/get-book-by-pages/{a}/{b}")
    public ResponseEntity getBookByPages(@PathVariable("a") int a , @PathVariable("b") int b){
        List<BookResponse> bookResponseList = bookService.getBookByPages(a , b);
        return new ResponseEntity<>(bookResponseList , HttpStatus.ACCEPTED);
    }

    // Names of all the authors who write a particular genre
    @GetMapping("/get-author-by-genre/{genre}")
    public ResponseEntity getAuthorByGenre(@PathVariable("genre") Genre genre){
        List<String> response = bookService.getAuthorByGenre(genre);
        return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
    }
}
